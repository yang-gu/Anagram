package delta.anagram;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AnagramOutputAdapter extends BaseAdapter {
    private List<List<String>> data;
    private Context context;

    public AnagramOutputAdapter(Context context, List<List<String>> data){
        setData(data);
        this.context = context;
    }

    public void setData(List<List<String>> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public List<String> getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout row;
        if(view == null) row = new LinearLayout(context);
        else row = (LinearLayout) view;
        refreshRow(row, getItem(i));
        return row;
    }

    private void refreshRow(LinearLayout row, List<String> words){
        ArrayList<TextView> wordsView = (ArrayList<TextView>) row.getTag();
        if(wordsView == null) wordsView = new ArrayList<TextView>();

        int wordsLength = words.size();

        for(TextView word : wordsView){
            word.setVisibility(View.GONE);
        }

        for(int i = 0; i < wordsLength;i++){
            TextView wordView;
            if(i >= wordsView.size()){
                wordView = new TextView(context);
                wordsView.add(wordView);
                row.addView(wordView);
            }
            wordView = wordsView.get(i);
            wordView.setText(words.get(i) + " ");
        }
    }
}
