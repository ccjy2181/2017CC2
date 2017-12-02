package kr.co.mapchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.R;
import kr.co.mapchat.recyclerview.SelectableAdapter;
import kr.co.mapchat.util.system.ImageManager;

public class MessageADT extends SelectableAdapter<MessageADT.ViewHolder> {
    private List<MessageDTO> mArrayList;
    private Context mContext;
    private MessageADT.ViewHolder.ClickListener clickListener;

    public MessageADT (Context context, List<MessageDTO> arrayList,MessageADT.ViewHolder.ClickListener clickListener) {
        this.mArrayList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;
    }

    // Create new views
    @Override
    public MessageADT.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_chat, null);

        MessageADT.ViewHolder viewHolder = new MessageADT.ViewHolder(itemLayoutView,clickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageADT.ViewHolder viewHolder, int position) {

        ImageManager imageManager = new ImageManager();

        viewHolder.tvName.setText(mArrayList.get(position).getTitle());
        viewHolder.tvText_count.setText(mArrayList.get(position).getAnswer()+"");
        if (isSelected(position)) {
            viewHolder.checked.setChecked(true);
            viewHolder.checked.setVisibility(View.VISIBLE);
        }else{
            viewHolder.checked.setChecked(false);
            viewHolder.checked.setVisibility(View.GONE);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        viewHolder.tvTime.setText(simpleDateFormat.format(mArrayList.get(position).getRegdate()).toString());
        viewHolder.mapImage.setImageBitmap(imageManager.decodingImageData(mArrayList.get(position).getImage_string()));
//        if (mArrayList.get(position).getOnline()){
//            viewHolder.onlineView.setVisibility(View.VISIBLE);
//        }else
            viewHolder.onlineView.setVisibility(View.INVISIBLE);

        viewHolder.tvLastChat.setText(mArrayList.get(position).getContents());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener  {

        public TextView tvName;
        public TextView tvTime;
        public TextView tvLastChat;
        public ImageView mapImage;
        public TextView tvText_count;
        public boolean online = false;
        private final View onlineView;
        public CheckBox checked;
        private MessageADT.ViewHolder.ClickListener listener;
        //private final View selectedOverlay;


        public ViewHolder(View itemLayoutView,MessageADT.ViewHolder.ClickListener listener) {
            super(itemLayoutView);

            this.listener = listener;

            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_user_name);
            //selectedOverlay = (View) itemView.findViewById(R.id.selected_overlay);
            tvTime = (TextView) itemLayoutView.findViewById(R.id.tv_time);
            tvText_count = (TextView) itemLayoutView.findViewById(R.id.text_count);
            tvLastChat = (TextView) itemLayoutView.findViewById(R.id.tv_last_chat);
            mapImage = (ImageView) itemLayoutView.findViewById(R.id.map_image);
            onlineView = (View) itemLayoutView.findViewById(R.id.online_indicator);
            checked = (CheckBox) itemLayoutView.findViewById(R.id.chk_list);

            itemLayoutView.setOnClickListener(this);

            itemLayoutView.setOnLongClickListener (this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition ());
            }
        }
        @Override
        public boolean onLongClick (View view) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition ());
            }
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);

            public boolean onItemLongClicked(int position);

            boolean onCreateOptionsMenu(Menu menu);
        }
    }
}