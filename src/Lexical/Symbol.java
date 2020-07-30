package Lexical;

public class Symbol {
    private String token;
    private Object value;
    public Symbol(String token) {
        this.token = token;
        this.value = token;
    }

    public Symbol(String token, Object value) {
        this.token = token;

        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

