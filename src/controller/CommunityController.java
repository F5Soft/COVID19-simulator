package controller;

import entity.Community;
import view.CommunityView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CommunityController {
    private final ArrayList<Community> communities;
    private final ArrayList<CommunityView> communityViews;
    private final int size;

    public CommunityController(ArrayList<Community> communities, ArrayList<CommunityView> communityViews) {
        this.communities = communities;
        this.communityViews = communityViews;
        this.size = communities.size();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int rand = (int) (Math.random() * size);
                if (!communities.get(rand).isSocialDistancing()) {
                    communityViews.get(rand).peopleCluster();
                }
            }
        }, 0, 2000);
    }
}
