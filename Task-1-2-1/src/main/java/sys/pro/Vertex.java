package sys.pro;

import java.util.Objects;

public class Vertex {
    private final int name;

    Vertex(int name) {
        this.name = name;
    }

    int getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
