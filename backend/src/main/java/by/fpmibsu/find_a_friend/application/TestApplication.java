package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.services.DIContainer;
import by.fpmibsu.find_a_friend.utils.Mediatr;
import by.fpmibsu.find_a_friend.utils.Request;
import by.fpmibsu.find_a_friend.utils.RequestHandler;

import java.net.InetSocketAddress;
import java.util.List;

public class TestApplication {
    public static class EmptyRequest extends Request<String> {
    }

    public static class EmptyRequestHandler extends RequestHandler<String, EmptyRequest> {

        @Override
        public String handle(EmptyRequest request) {
            return "Hello, world!";
        }
    }

    public static class CountRequest extends Request<String> {
    }

    public static class CountHolder {
        private int count = 0;

        public void increment() {
            ++count;
        }

        public int getCount() {
            return count;
        }
    }

    public static class CountHandler extends RequestHandler<String, CountRequest> {
        private final CountHolder countHolder;

        public CountHandler(CountHolder countHolder) {
            this.countHolder = countHolder;
        }

        @Override
        public String handle(CountRequest request) {
            System.out.println("Called");
            String response = "This page was visited " + countHolder.getCount() + " times!";
            countHolder.increment();
            return response;
        }
    }

    public static void main(String[] args) {
        int serverPort = 8080;
        var diContainer = new DIContainer();
        diContainer.addService(CountHolder.class, DIContainer.ServiceType.SINGLETON, CountHolder::new);
        var mediatr = new Mediatr(diContainer);
        mediatr.registerHandler(EmptyRequestHandler.class, String.class, EmptyRequest.class);
        mediatr.registerHandler(CountHandler.class, String.class, CountRequest.class);
        List<Endpoint<?, ?>> endpoints = List.of(new Endpoint<>("/", EmptyRequest.class, String.class, "GET"),
                new Endpoint<>("/count", CountRequest.class, String.class, "GET"));
        var application = new Application(new InetSocketAddress(serverPort), 0, endpoints,
                diContainer,
                List.of(new EndpointsSender(endpoints, mediatr)));
        application.start();
    }
}
