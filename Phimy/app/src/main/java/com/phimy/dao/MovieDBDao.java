package com.phimy.dao;

import android.content.Context;

import com.phimy.dao.database.DatabaseHelper;
import com.phimy.dao.database.FavoritoDAO;
import com.phimy.dao.database.MovieDAO;
import com.phimy.model.FavoritoDB;
import com.phimy.model.MovieDB;
import com.phimy.model.MovieDBContainer;

import java.util.ArrayList;
import java.util.List;

import Utils.ResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDBDao extends DaoHelper {
    private ServiceMoviesDB serviceMovies;
    private String api_key="5aa2e212bfa0373c59c3494bb068827f";
    Call<MovieDBContainer> call;
    List<MovieDB> favoritosMovieDBS=new ArrayList<MovieDB>();

    public MovieDBDao() {
        super("https://api.themoviedb.org/3/");
        serviceMovies = retrofit.create(ServiceMoviesDB.class);
    }

    public List<MovieDB> getFavoritosMovieDBS(Context context) {
        MovieDAO movieDao = DatabaseHelper
                .getInstance(context.getApplicationContext())
                .getMovieDbDAO();

        List<MovieDB> movies = movieDao.buscarMovies();
        return favoritosMovieDBS;
    }

    public void getMovies(final ResultListener<List<MovieDB>> listenerDelController, Integer search){

        switch(search) {
            case 0 :
                this.call = serviceMovies.getPopularMovies(api_key);
                break; // optional
            case 1 :
                this.call = serviceMovies.getPopularTv(api_key);
                break; // optional
            case 2 :
                this.call = serviceMovies.getNowPlaying(api_key);
                break; // optional
                // You can have any number of case statements.
            default : // Optional
                this.call = serviceMovies.getPopularMovies(api_key);
                break;
        }
        this.call.enqueue(new Callback<MovieDBContainer>() {
            @Override
            public void onResponse(Call<MovieDBContainer> call, Response<MovieDBContainer> response) {
                MovieDBContainer movieContainer = response.body();
                List<MovieDB> movies = movieContainer.getMisMovies();
                listenerDelController.finish(movies);
            }
            @Override
            public void onFailure(Call<MovieDBContainer> call, Throwable t) {
                String i= "hola";
            }
        });
    }

    public void getFavoritos(final ResultListener<List<MovieDB>> listenerDelController){
        listenerDelController.finish(favoritosMovieDBS);
    }

    //Administración favoritos ROOM database
    public void addFavoritos(Context context, MovieDB movieDB){
        //if (!favoritosMovieDBS.contains(movieDB)) {
        FavoritoDAO favoritoDao = DatabaseHelper
                .getInstance(context.getApplicationContext())
                .getFavoritoDbDAO();
        favoritoDao.insertarFavorito(this.convertMovieDB(movieDB));

    }

    public void removeFavoritos(Context context, MovieDB movieDB){
        //ROOM delete favorito
        FavoritoDAO favoritoDao = DatabaseHelper
                .getInstance(context.getApplicationContext())
                .getFavoritoDbDAO();
        favoritoDao.deleteFavorito(this.convertMovieDB(movieDB));

    }

    //Elimina del mismo taba de favoritos - Le viene un favorito -
    public void removeFavoritosDup(Context context, FavoritoDB favoritoDB){
        //ROOM delete favorito
        FavoritoDAO favoritoDao = DatabaseHelper
                .getInstance(context.getApplicationContext())
                .getFavoritoDbDAO();
        favoritoDao.deleteFavorito(favoritoDB);

    }

    public List<FavoritoDB> getFavoritosDB(Context context) {
        FavoritoDAO favoritoDao = DatabaseHelper
                .getInstance(context.getApplicationContext())
                .getFavoritoDbDAO();
        List<FavoritoDB> favoritos = favoritoDao.buscarFavoritos();
        return favoritos;
    }

    private FavoritoDB convertMovieDB(MovieDB movieDB){
        FavoritoDB favoritoDB= new FavoritoDB();
        favoritoDB.setId(movieDB.getId());
        favoritoDB.setTitle(movieDB.getTitle());
        favoritoDB.setPoster_path(movieDB.getPoster_path());
        favoritoDB.setVote_count(movieDB.getVote_count());
        favoritoDB.setPopularity(movieDB.getPopularity());
        favoritoDB.setOverview(movieDB.getRelease_date());
        favoritoDB.setOverview(movieDB.getOverview());

        return favoritoDB;
    }
}