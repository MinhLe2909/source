package tamhoang.ldpro4.ui.fragment;

import java.util.Iterator;
import java.util.Objects;

public class Frag_ChaytrangNew$$ExternalSynthetic0 {
    public static String m0(CharSequence charSequence, Iterable iterable) {
        Objects.requireNonNull(charSequence, "delimiter");
        StringBuilder sb = new StringBuilder();
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            while (true) {
                sb.append((CharSequence) it.next());
                if (!it.hasNext()) {
                    break;
                }
                sb.append(charSequence);
            }
        }
        return sb.toString();
    }
}
