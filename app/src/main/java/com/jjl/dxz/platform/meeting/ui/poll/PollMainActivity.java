package com.jjl.dxz.platform.meeting.ui.poll;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.constant.BundleKey;

public class PollMainActivity extends AppCompatActivity {

    private int meetingNumber;
    private final PollListFragment pollListFragment = new PollListFragment();
    private final NewPollFragment newPollFragment = new NewPollFragment();
    private final PollDetailFragment pollDetailFragment = new PollDetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_main);
        meetingNumber = getIntent().getIntExtra(BundleKey.MEETING_NUMBER, -1);
        gotoPollList();
    }

    public void gotoPollList() {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MEETING_NUMBER, meetingNumber);
        pollListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pollListFragment).commitNow();
    }

    public void gotoNewPoll() {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MEETING_NUMBER, meetingNumber);
        newPollFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newPollFragment).commitNow();
    }

    public void gotoPollDetail() {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MEETING_NUMBER, meetingNumber);
        pollDetailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pollDetailFragment).commitNow();
    }
}