package darkvador.nurseproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mathieu on 25/03/2015
 */
public class ActeAdapter extends BaseAdapter {
    private List<Acte> listActe;
    private LayoutInflater layoutInflater;
    public ActeAdapter(Context context, List<Acte> vListActe) {
        layoutInflater = LayoutInflater.from(context);
        listActe = vListActe;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listActe.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listActe.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.vueacte, null);
            holder.textViewIdentifiant = (TextView) convertView
                    .findViewById(R.id.vueId);
            holder.textViewLibelle = (TextView) convertView
                    .findViewById(R.id.vueLibelle);
            holder.textViewDuree = (TextView) convertView
                    .findViewById(R.id.vueDuree);
            holder.textViewDescription = (TextView) convertView
                    .findViewById(R.id.vueDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(238, 233, 233));
        } else {
            convertView.setBackgroundColor(Color.rgb(255, 255, 255));
        }
        holder.textViewIdentifiant.setText(listActe.get(position)
                .getId());
        holder.textViewLibelle.setText(listActe.get(position).getLibelle());
        holder.textViewDuree.setText(listActe.get(position).getDuree());
        holder.textViewDescription.setText(listActe.get(position).getDescription());
        return convertView;
    }

    private class ViewHolder {
        TextView textViewIdentifiant;
        TextView textViewDuree;
        TextView textViewLibelle;
        TextView textViewDescription;

    }
}
