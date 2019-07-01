package com.oganbelema.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.oganbelema.bakingapp.ui.MainActivity;
import com.oganbelema.network.data.Ingredient;
import com.oganbelema.network.data.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SHARED_PREFERENCE_ID, Context.MODE_PRIVATE);

        String recipeJson = sharedPreferences.getString(Constants.RECIPE_KEY, null);

        Gson gson = new Gson();

        Recipe recipe = gson.fromJson(recipeJson, Recipe.class);

        StringBuilder widgetText = new StringBuilder();

        widgetText.append(recipe.getName());

        widgetText.append("\n ***Ingredients***");

        for (Ingredient ingredient : recipe.getIngredients()) {
            widgetText.append("\n");
            widgetText.append(ingredient.getIngredient());
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setTextViewText(R.id.appwidget_text, widgetText.toString());

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(Constants.RECIPE_KEY, recipe);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

