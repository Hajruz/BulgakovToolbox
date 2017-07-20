package ru.idaspin.helperforbulgakov.activities_old.budget.budgetTools;

import android.content.Context;

import ru.idaspin.helperforbulgakov.utils.SharedPrefsUtil;

public class Budget {

    public static final String RUB_TEXT = " руб.";
    private float currentBudget;
    private Context context;

    public Budget(Context context){
        currentBudget = SharedPrefsUtil.getFloatPreference(context, "budget", 0);
        this.context = context;
    }

    public void plusBudget(float plus){
        currentBudget += plus;
        if(currentBudget != 0) {
            currentBudget = rintBudget(currentBudget);
        }
        saveBudget();
    }

    public boolean minusBudget(float minus){
        if(currentBudget >= minus){
            currentBudget -= minus;
            if(currentBudget != 0) {
                currentBudget = rintBudget(currentBudget);
            }
            saveBudget();
            return true;
        }
        return false;
    }

    private void saveBudget(){
        SharedPrefsUtil.setFloatPreference(context, "budget", currentBudget);
    }

    public static float rintBudget(float sum){
        return  (float) (Math.rint(100.0 * sum)/ 100.0);
    }

    public float getBudget(){
        return currentBudget;
    }
}
