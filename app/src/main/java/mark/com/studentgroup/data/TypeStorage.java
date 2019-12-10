package mark.com.studentgroup.data;

import java.util.ArrayList;
import java.util.List;

public class TypeStorage {
    int id;
    String title;

    public TypeStorage(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<TypeStorage> getListType() {
        List<TypeStorage> list = new ArrayList<>();
        list.add(new TypeStorage(0, "в прокате"));
        list.add(new TypeStorage(1, "в  аудиотеке"));
        return list;
    }
}
