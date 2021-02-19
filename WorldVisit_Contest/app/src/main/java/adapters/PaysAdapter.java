package adapters;
import java.util.List;

import com.example.worldvisit_contest.MainActivity;
import com.example.worldvisit_contest.R;
import objects.Pays;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PaysAdapter extends ArrayAdapter<Pays>{
    private Activity activity;
    private List<Pays> items;
    private Pays objBean;
    private int row;
    private Context c;

    public PaysAdapter(Activity act, int resource, List<Pays> arrayList, Context contexte) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
        this.c = contexte;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((items == null) || ((position + 1) > items.size()))
            return view;

        objBean = items.get(position);

        holder.nomPays = (TextView) view.findViewById(R.id.nomPays);
        holder.capital = (TextView) view.findViewById(R.id.capital);
        holder.drapeau = (ImageView) view.findViewById(R.id.drapeau);

        if (holder.nomPays != null && null != objBean.getNomPays()
                && objBean.getNomPays().trim().length() > 0) {
            holder.nomPays.setText(Html.fromHtml(objBean.getNomPays()));
        }

        if (holder.capital != null && null != objBean.getCapital()
                && objBean.getCapital().trim().length() > 0) {
            holder.capital.setText(Html.fromHtml(objBean.getCapital()));
        }

        if (holder.drapeau != null && null != objBean.getDrapeau()
                && objBean.getDrapeau().trim().length() > 0) {
            holder.drapeau.setImageResource(c.getResources().getIdentifier("com.example.geoworld:drawable/" + objBean.getDrapeau(), null, null));
        }

        return view;
    }

    public class ViewHolder {
        public TextView nomPays, capital;
        public ImageView drapeau;
    }
}