package eventloops;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(2)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS));

//    vertx.deployVerticle(new EventLoopExample());
    vertx.deployVerticle(EventLoopExample.class.getName(),new DeploymentOptions().setInstances(4));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}",getClass().getName());
    LOG.info("Start {}",getClass().getName());
    startPromise.complete();
    Thread.sleep(5000);
  }
}
