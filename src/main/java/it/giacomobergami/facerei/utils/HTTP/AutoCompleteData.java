package it.giacomobergami.facerei.utils.HTTP;

public class AutoCompleteData {
    private final String label;
    private final String value;

    public AutoCompleteData(String _label, String _value) {
        super();
        this.label = _label;
        this.value = _value;
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutoCompleteData)) return false;

        AutoCompleteData that = (AutoCompleteData) o;

        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}