import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
//Bu sınıfta association rule tutulmaktadır.
public class Association<I> {

    private final Set<I> ilk = new HashSet<>();
    private final Set<I> devam = new HashSet<>();
    private double conf;

    public Association(Set<I> ilk, 
                           Set<I> devam, 
                           double conf) {
        Objects.requireNonNull(ilk, "Ilklendirilmemis.");
        Objects.requireNonNull(devam, "Devam Yok.");
        this.ilk.addAll(ilk);
        this.devam.addAll(devam);
        this.conf = conf;
    }

    public Association(Set<I> ilk, Set<I> devam) {
        this(ilk, devam, Double.NaN);
    }

    public Set<I> getIlk() {
        return Collections.<I>unmodifiableSet(ilk);
    }

    public Set<I> getDevam() {
        return Collections.<I>unmodifiableSet(devam);
    }

    public double getConfidence() {
        return conf;
    }
    //association rulleri yazdırmak için 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Arrays.toString(ilk.toArray()));
        sb.append(" -> ");
        sb.append(Arrays.toString(devam.toArray()));
        sb.append(": ");
        sb.append(conf);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return ilk.hashCode() ^ devam.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Association<I> other = (Association<I>) obj;

        return ilk.equals(other.ilk) &&
               devam.equals(other.devam);
    }
}