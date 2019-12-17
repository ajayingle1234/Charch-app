package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.FAQBean;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.CustomViewHolder> {

    private Context context;
    List<FAQBean> faqBeanList;

    public FAQAdapter(Context context, List<FAQBean> faqBeanList) {
        this.context = context;
        this.faqBeanList = faqBeanList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView txtFAQ;
        private Button btnFAQ;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtFAQ = mView.findViewById(R.id.txt_faq1);
            btnFAQ = mView.findViewById(R.id.btn_faq1);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_faqs, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final FAQBean faqBean = faqBeanList.get(position);

        holder.btnFAQ.setText(faqBean.getFaq_Question());
        holder.txtFAQ.setText(faqBean.getFaq_Question());


        holder.btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtFAQ.getVisibility() == View.VISIBLE) {
                    holder.txtFAQ.setVisibility(View.GONE);
                } else {
                    holder.txtFAQ.setVisibility(View.VISIBLE);
                    holder.txtFAQ.setText(faqBean.getFaq_Answer());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqBeanList.size();
    }
}