package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample {

  private static final Logger LOG = LoggerFactory.getLogger(RequestResponseExample.class);
  public static final String ADDRESS = "new.request.address";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();

    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());

  }

  static class RequestVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      String message = "Hello World!";
      LOG.debug("Sending {}", message);
      eventBus.<String>request(ADDRESS, message, reply -> {
        if (reply.succeeded()) {
          LOG.debug("Response: {}", reply.result().body());
        } else {
          LOG.debug("No response");
        }
        ;
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(ADDRESS, message -> {
        LOG.debug("Received: {}", message.body());
        message.reply("Received message '" + message.body() + "'", new DeliveryOptions().setSendTimeout(3000));
      });
    }
  }
}
