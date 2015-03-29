package darkvador.nurseproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class calendrier extends Fragment implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener{
    private WeekView mWeekView;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private Model model = new Model();
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    public calendrier() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendrier, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get a reference for the week view in the layout.
        setHasOptionsMenu(true);

    }
    @Override
    public void onStart() {
        super.onStart();
        mWeekView = (WeekView) getView().findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
       getActivity().findViewById(R.id.back).setBackgroundColor(R.color.ColorPrimary);
        getActivity().findViewById(R.id.imageView2).setVisibility(View.GONE);
        menuOptionSelected();
    }

    private void menuOptionSelected() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.action_today:
                                mWeekView.goToToday();
                                return true;
                            case R.id.action_day_view:
                                if (mWeekViewType != TYPE_DAY_VIEW) {
                                    item.setChecked(!item.isChecked());
                                    mWeekViewType = TYPE_DAY_VIEW;
                                    mWeekView.setNumberOfVisibleDays(1);
                                    // Lets change some dimensions to best fit the view.
                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                }
                                return true;
                            case R.id.action_three_day_view:
                                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                                    item.setChecked(!item.isChecked());
                                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                                    mWeekView.setNumberOfVisibleDays(3);

                                    // Lets change some dimensions to best fit the view.
                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                }
                                return true;
                            case R.id.action_week_view:
                                if (mWeekViewType != TYPE_WEEK_VIEW) {
                                    item.setChecked(!item.isChecked());
                                    mWeekViewType = TYPE_WEEK_VIEW;
                                    mWeekView.setNumberOfVisibleDays(7);

                                    // Lets change some dimensions to best fit the view.
                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                                }
                                return true;
                            case R.id.action_maj:
                                String[] mesparams = { "http://rdelaporte.alwaysdata.net/importVisite.php" };
                                mThreadCon = new Async (calendrier.this).execute(mesparams);
                                return true;
                            case R.id.action_websearch:
                                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                });
    }


    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Populate the week view with some events.
//        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth-1);
//        startTime.set(Calendar.YEAR, newYear);
//        Calendar endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR, 1);
//        endTime.set(Calendar.MONTH, newMonth-1);
//        WeekViewEvent event = new WeekViewEvent(1, startTime.getTime()+": Luc de l'échec", startTime, endTime);
//        event.setColor(getResources().getColor(R.color.event_color_02));
//        //event.setName("bijour");
//        events.add(event);
        ArrayList<Visite> listeVisite=model.listeVisite();
        Random rand = new Random();
        ArrayList<Integer> listeCouleur=new ArrayList<>();
        listeCouleur.add(getResources().getColor(R.color.event_color_02));
        listeCouleur.add(getResources().getColor(R.color.event_color_01));
        listeCouleur.add(getResources().getColor(R.color.event_color_03));
        listeCouleur.add(getResources().getColor(R.color.event_color_04));
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (Visite v: listeVisite) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, v.getHeureDebut());
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.DAY_OF_MONTH, v.getJour());
            startTime.set(Calendar.MONTH, v.getMois()-1);
            startTime.set(Calendar.MONTH, v.getMois()-(newMonth-(v.getMois())+1));
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, v.getDuree());
            WeekViewEvent event = new WeekViewEvent(v.getIdVisite(), v.getHeureDebut()+"h: rendez-vous avec:"+v.getNomPatient()+" "+v.getPrenomPatient(), startTime, endTime);
            event.setColor(listeCouleur.get(rand.nextInt(4)));
            //event.setName("bijour");
            events.add(event);
        }

        return events;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_test, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Intent myIntent = new Intent(getView().getContext(), afficheVisite.class);
        myIntent.putExtra("id", Long.toString(event.getId()));
        startActivity(myIntent);
        Toast.makeText(getActivity(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), "Long pressed event: " + event.getId(), Toast.LENGTH_SHORT).show();
    }
    public void retourImport(StringBuilder sb)
    {
        JsonElement json = new JsonParser().parse(sb.toString());
        JsonArray varray = json.getAsJsonArray();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
        ArrayList<Visite> listeVisite = new ArrayList<>();
        for (JsonElement obj : varray) {
            Visite visite = gson.fromJson(obj.getAsJsonObject(), Visite.class);
            visite.setdate();
            listeVisite.add(visite);
        }
        model.deleteVisite();
        model.addVisite(listeVisite);
        //alertmsg("Import terminé","Les "+listePatient.size()+" patients ont été ajouté à la base");
        mWeekView.goToToday();
    }
}
