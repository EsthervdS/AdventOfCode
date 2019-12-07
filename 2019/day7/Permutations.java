import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations<T> {
    public ArrayList<ArrayList<T>> permute(ArrayList<T> input) {
        ArrayList<ArrayList<T>> output = new ArrayList<ArrayList<T>>();
        if (input.isEmpty()) {
            output.add(new ArrayList<T>());
            return output;
        }
        ArrayList<T> list = new ArrayList<T>(input);
        T head = list.get(0);
        ArrayList<T> rest = new ArrayList(list.subList(1, list.size()));
        for (ArrayList<T> permutations : permute(rest)) {
            ArrayList<ArrayList<T>> subLists = new ArrayList<ArrayList<T>>();
            for (int i = 0; i <= permutations.size(); i++) {
                ArrayList<T> subList = new ArrayList<T>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }
}
