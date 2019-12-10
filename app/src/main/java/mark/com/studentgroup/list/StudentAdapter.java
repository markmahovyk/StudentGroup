package mark.com.studentgroup.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mark.com.studentgroup.R;
import mark.com.studentgroup.data.Magazine;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Magazine> students;
    private OnItemClick onItemClick;

    public void setStudents(List<Magazine> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        viewHolder.bind(students.get(pos));
    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFirstName;
        TextView textLastName;
        View deleteLayout;
        View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstName = itemView.findViewById(R.id.text_first_name);
            textLastName = itemView.findViewById(R.id.text_last_name);
            deleteLayout = itemView.findViewById(R.id.delete_layout);
            root = itemView.findViewById(R.id.root);
        }

        public void bind(Magazine magazine) {
            textLastName.setText(magazine.getContent());
            textFirstName.setText(magazine.getFullName());
            root.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onItemClick(magazine.getId());
                }
            });
            deleteLayout.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onDeleteClick(magazine.getId());
                }
            });
        }
    }
}
