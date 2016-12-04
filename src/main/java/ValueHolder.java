import lombok.Getter;

public class ValueHolder<T> {
    private T value;
    @Getter private boolean isSet = false;

    public T getValue() {
        if (!isSet)
            throw new NullPointerException();
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.isSet = true;
    }
}
