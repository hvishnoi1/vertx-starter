package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleB extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    System.out.println("Started " + getClass().getName());
    LOG.debug("Started {}", getClass().getName());
    vertx.deployVerticle(new VerticleBA());
    vertx.deployVerticle(new VerticleBB(), whenDeployed -> {
//      System.out.println("Deployed " + VerticleBB.class.getName());
      LOG.debug("Deployed {}" , VerticleBB.class.getName());
    });
    startPromise.complete();
  }
}
