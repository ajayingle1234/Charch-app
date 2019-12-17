package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hardcastle.com.churchapplication.R;

public class DailyActivityAdapter extends RecyclerView.Adapter<DailyActivityAdapter.CustomViewHolder> {

    private Context context;
    List<Integer> daylist;
    private boolean flag;

    public DailyActivityAdapter(Context context, List<Integer> daylist) {
        this.context = context;
        this.daylist = daylist;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView txtDate;
        private TextView txtWeekDay;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtWeekDay = mView.findViewById(R.id.txt_weekday);
            txtDate = mView.findViewById(R.id.txt_date);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dayofmonth, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        int day = daylist.get(position);

        holder.txtDate.setText(String.valueOf(day));

        holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    holder.txtDate.setBackground(context.getResources().getDrawable(R.drawable.white_button));
                    holder.txtDate.setTextColor(context.getResources().getColor(R.color.colorPrimaryButton));
                    flag = false;
                } else {
                    holder.txtDate.setBackground(context.getResources().getDrawable(R.drawable.blue_button));
                    holder.txtDate.setTextColor(context.getResources().getColor(R.color.white));
                    flag = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return daylist.size();
    }
}