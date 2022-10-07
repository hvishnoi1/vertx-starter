package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    System.out.println("Started "+ getClass().getName());
    LOG.debug("Started {}", getClass().getName());
    vertx.deployVerticle(new VerticleAA(),whenDeployed->{
//      System.out.println("Deployed "+VerticleAA.class.getName());
     LOG.debug("Deployed {}", VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    startPromise.complete();
  }
}
