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
 * Created by darkvador on 11/03/15.
 */
public class PatientAdapter extends BaseAdapter {
    private List<Patient> listPatient;
    private LayoutInflater layoutInflater;
    public PatientAdapter(Context context, List<Patient> vListPatient) {
        layoutInflater = LayoutInflater.from(context);
        listPatient = vListPatient;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listPatient.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listPatient.get(position);
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
            convertView = layoutInflater.inflate(R.layout.vuepatient, null);
            holder.textViewNom = (TextView) convertView
                    .findViewById(R.id.vueNom);
            holder.textViewPrenom = (TextView) convertView
                    .findViewById(R.id.vuePrenom);
            holder.textViewTelephone = (TextView) convertView
                    .findViewById(R.id.vueTl);
            holder.textViewCodePostal = (TextView) convertView
                    .findViewById(R.id.vueCp);
            holder.textViewAdresse = (TextView) convertView
                    .findViewById(R.id.vueAdresse);
            holder.textViewVille = (TextView) convertView
                    .findViewById(R.id.vueVille);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.textViewNom.setText(listPatient.get(position).getNom());
        holder.textViewPrenom.setText(listPatient.get(position).getPrenom());
        String s = listPatient.get(position).getTelephone();
        s = String.format("%s.%s.%s.%s.%s", s.substring(0, 2),
                s.substring(2, 4), s.substring(4, 6), s.substring(6, 8),
                s.substring(8, 10));
        holder.textViewTelephone.setText(s);
        holder.textViewAdresse.setText(listPatient.get(position).getAdresse());
        holder.textViewCodePostal.setText(listPatient.get(position)
                .getCodePostal());
        holder.textViewVille.setText(listPatient.get(position).getVille());
        return convertView;
    }

    private class ViewHolder {

        TextView textViewNom;
        TextView textViewPrenom;
        TextView textViewTelephone;
        TextView textViewAdresse;
        TextView textViewCodePostal;
        TextView textViewVille;
    }
}
