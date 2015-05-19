package fr.ig2i.unesaisonauzoo.callback;

import android.view.View;

import java.util.Date;

import fr.ig2i.unesaisonauzoo.view.fragment.ProgrammeTvFragment;

/**
 * Created by Paul on 19/05/2015.
 */
public class CalendarButtonListener implements View.OnClickListener {
    private ProgrammeTvFragment _fragment;
    private Date _start;
    private String _progName;
    private Date _end;

    public CalendarButtonListener(ProgrammeTvFragment _fragment, Date start, String progName, Date end) {
        this._fragment = _fragment;
        this._start = start;
        this._progName = progName;
        this._end = end;
    }

    @Override
    public void onClick(View v) {
        _fragment.getmListener().OnCalendarButtonClicked(_start, _progName, _end);
    }
}
