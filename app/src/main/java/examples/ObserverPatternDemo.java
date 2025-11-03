package examples;

import utils.Utils;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletableFuture;

public class ObserverPatternDemo {

  // Traditional Observer Pattern
  // Subject interface
  interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
  }

  // Observer interface
  interface Observer {
    void update(String message);
  }

  // Concrete Subject
  static class NewsAgency implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String news;

    @Override
    public void registerObserver(Observer observer) {
      observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
      observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
      for (Observer observer : observers) {
        observer.update(news);
      }
    }

    public void setNews(String news) {
      this.news = news;
      notifyObservers();
    }
  }

  // Concrete Observers
  static class NewsChannel implements Observer {
    private final String name;

    public NewsChannel(String name) {
      this.name = name;
    }

    @Override
    public void update(String news) {
      System.out.println(name + " received news: " + news);
    }
  }

  // Generic Observer Pattern with Type Safety
  static class EventManager<T> {
    private final Map<String, List<EventListener<T>>> listeners = new HashMap<>();

    public void subscribe(String eventType, EventListener<T> listener) {
      listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(String eventType, EventListener<T> listener) {
      List<EventListener<T>> users = listeners.get(eventType);
      if (users != null) {
        users.remove(listener);
      }
    }

    public void notify(String eventType, T data) {
      List<EventListener<T>> users = listeners.get(eventType);
      if (users != null) {
        for (EventListener<T> listener : users) {
          listener.update(data);
        }
      }
    }
  }

  interface EventListener<T> {
    void update(T data);
  }

  // Example using modern Java Flow API (Reactive Streams)
  static class NewsSubscriber implements Flow.Subscriber<String> {
    private final String name;
    private Flow.Subscription subscription;

    public NewsSubscriber(String name) {
      this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      this.subscription = subscription;
      subscription.request(1); // Request one item
    }

    @Override
    public void onNext(String item) {
      System.out.println(name + " received: " + item);
      subscription.request(1); // Request next item
    }

    @Override
    public void onError(Throwable throwable) {
      System.out.println(name + " got error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
      System.out.println(name + " completed subscription");
    }
  }

  // Example with PropertyChangeSupport
  static class WeatherStation {
    private final java.beans.PropertyChangeSupport support;
    private int temperature;

    public WeatherStation() {
      support = new java.beans.PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
      support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
      support.removePropertyChangeListener(pcl);
    }

    public void setTemperature(int newTemperature) {
      int oldTemperature = this.temperature;
      this.temperature = newTemperature;
      support.firePropertyChange("temperature", oldTemperature, newTemperature);
    }
  }

  public static void demonstrateObserver() {
    Utils.printLine("Observer Pattern Examples");

    // 1. Traditional Observer Pattern
    System.out.println("Traditional Observer Pattern Example:");
    NewsAgency newsAgency = new NewsAgency();
    NewsChannel channel1 = new NewsChannel("Channel 1");
    NewsChannel channel2 = new NewsChannel("Channel 2");

    newsAgency.registerObserver(channel1);
    newsAgency.registerObserver(channel2);
    newsAgency.setNews("Breaking: Java 25 released!");

    // Remove one observer
    System.out.println("\nAfter removing Channel 2:");
    newsAgency.removeObserver(channel2);
    newsAgency.setNews("Update: Java 25 features announced!");

    // 2. Generic Observer Pattern
    System.out.println("\nGeneric Observer Pattern Example:");
    EventManager<String> eventManager = new EventManager<>();

    eventManager.subscribe("email", data -> System.out.println("Sending email: " + data));
    eventManager.subscribe("sms", data -> System.out.println("Sending SMS: " + data));

    eventManager.notify("email", "Hello subscribers!");
    eventManager.notify("sms", "Quick update!");

    // 3. Java Flow API (Reactive Streams)
    System.out.println("\nJava Flow API Example:");
    SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    // Create subscribers
    NewsSubscriber sub1 = new NewsSubscriber("Subscriber 1");
    NewsSubscriber sub2 = new NewsSubscriber("Subscriber 2");

    // Subscribe
    publisher.subscribe(sub1);
    publisher.subscribe(sub2);

    // Publish items
    publisher.submit("Breaking News 1");
    publisher.submit("Breaking News 2");

    // Close the publisher
    CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(100); // Wait for processing
        publisher.close();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    // 4. PropertyChangeSupport Example
    System.out.println("\nPropertyChangeSupport Example:");
    WeatherStation weatherStation = new WeatherStation();

    weatherStation.addPropertyChangeListener(evt -> System.out.println("Temperature changed from " +
        evt.getOldValue() + " to " + evt.getNewValue()));

    weatherStation.setTemperature(24);
    weatherStation.setTemperature(27);
  }
}