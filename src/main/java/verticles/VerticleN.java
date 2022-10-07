package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    System.out.println("Started " + getClass().getName() + " on Thread " + Thread.currentThread().getName() + " with config " + config().toString());
    LOG.debug("Started {} on Thread {} with config {}",getClass().getName(),Thread.currentThread().getName(),config().toString());
    startPromise.complete();
  }
}
