package io.theduykh.ata.driver;

public abstract class AtaValidation {

    protected final long TIMEOUT = 10;
    protected final AtaDriver driver;
    protected final Object target;

    public AtaValidation(AtaDriver driver, Object target) {
        this.driver = driver;
        this.target = target;
    }

    public abstract void visible();

    public abstract void invisible();

    public abstract void hasText(String text);
}
