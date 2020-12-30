import org.junit.Assert;

public class AbstractZkTest {

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }
}
