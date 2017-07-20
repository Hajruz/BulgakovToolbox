package ru.idaspin.helperforbulgakov.activities_old.budget.budgetTools;

import ru.idaspin.helperforbulgakov.activities_old.budget.BudgetActivity;

/**
 * Created by User.
 * Date: 17.07.2017
 * Time: 1:27 PM
 */

public class BudgetActivityTools {

    private static BudgetActivityTools budgetActivityTools;
    private static BudgetActivity budgetActivity;

    private BudgetActivityTools(BudgetActivity budgetActivity){
        this.budgetActivity = budgetActivity;
    }

    public static void setBudgetActivity(BudgetActivity budgetActivity){
        if(budgetActivityTools == null){
            budgetActivityTools = new BudgetActivityTools(budgetActivity);
        }
    }

    public static BudgetActivity getInstance(){
        return budgetActivity;
    }
}
