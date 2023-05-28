package test.fpmibsu.findafriend.application.mediatr;

import by.fpmibsu.findafriend.application.mediatr.*;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MediatrTests {
    private static class TestRequest extends Request<Integer> {}
    public static class TestHandler extends RequestHandler<Integer, TestRequest> {
        @Override
        public Integer handle(TestRequest request) throws Exception {
            return 100;
        }
    }
    @Test
    public void registersHandlerAndSendsRequest() {
        var handlers = new HandlersDataList();
        handlers.registerHandler(TestHandler.class, TestRequest.class);
        var sp = Mockito.mock(ServiceProvider.class);
        var mediatr = new Mediatr(sp, handlers);
        var result = mediatr.send(new TestRequest());
        assertEquals(result, 100);
    }

    @Test(expectedExceptions = NoHandlerException.class)
    public void throwsIfHandlerNotRegistered() {
        var handlers = new HandlersDataList();
        var handler = new TestHandler();
        var sp = Mockito.mock(ServiceProvider.class);
        Mockito.doReturn(handler).when(sp).getRequiredService(Mockito.any());
        var mediatr = new Mediatr(sp, handlers);
        var result = mediatr.send(new TestRequest());
    }
}
