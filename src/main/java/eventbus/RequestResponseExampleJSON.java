package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExampleJSON {

  private static final Logger LOG = LoggerFactory.getLogger(RequestResponseExampleJSON.class);
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
      JsonObject message = new JsonObject()
        .put("message", "Hello World!")
        .put("version", 1);
      LOG.debug("Sending {}", message);
      eventBus.<JsonArray>request(ADDRESS, message, reply -> {
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
      vertx.eventBus().<JsonObject>consumer(ADDRESS, message -> {
        LOG.debug("Received: {}", message.body());
        message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
