package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Point2PointExample {
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Receiver());
    vertx.deployVerticle(new Sender());
  }

  static class Sender extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        vertx.eventBus().send(Sender.class.getName(), "Sending new message");
      });
    }
  }

  static class Receiver extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();

      vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
        LOG.debug("Received: {}", message.body());
      });
    }
  }
}
