package com.spintech.testtask.controller;

import com.spintech.testtask.repository.user.entity.User;
import com.spintech.testtask.service.actor.ActorService;
import com.spintech.testtask.service.actor.model.ActorDto;
import com.spintech.testtask.service.tv.TvService;
import com.spintech.testtask.service.tv.model.TvDto;
import com.spintech.testtask.service.user.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVController {
    private final UserService userService;

    private final TmdbApi tmdbApi;
    private final TvService tvService;
    private final ActorService actorService;

    public TVController(UserService userService, TmdbApi tmdbApi, TvService tvService, ActorService actorService) {
        this.userService = userService;
        this.tmdbApi = tmdbApi;
        this.tvService = tvService;
        this.actorService = actorService;
    }

    @RequestMapping(value = "/popular", method = POST)
    public ResponseEntity<String> popular(@RequestParam String email,
                                  @RequestParam String password) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularTVShows();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @GetMapping(value = "/watched")
    public ResponseEntity<List<TvDto>> watched(@RequestParam String email,
                                                   @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(tvService.getWatchedTvList(user.getId()));
    }

    @PutMapping(value = "/watched")
    public ResponseEntity<Void> updateWatched(@RequestParam String email,
                                                   @RequestParam String password,
                                                         @RequestParam long tvId, @RequestParam boolean isWatched) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        tvService.updateWatchedTvList(user.getId(), tvId, isWatched);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/actor")
    public ResponseEntity<List<ActorDto>> favouriteActors(@RequestParam String email,
                                                          @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(actorService.getFavouriteList(user.getId()));
    }

    @PostMapping(value = "/actor")
    public ResponseEntity<Void> addActor(@RequestParam String email,
                                                   @RequestParam String password,
                                                         @RequestParam long actorId) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        actorService.addActorToFavourite(actorId, user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/actor")
    public ResponseEntity<Void> deleteActor(@RequestParam String email,
                                                   @RequestParam String password,
                                                         @RequestParam long actorId) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        actorService.deleteActorFromFavourite(actorId, user.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/recommendation")
    public ResponseEntity<List<TvDto>> favouriteActorsTvs(@RequestParam String email,
                                                          @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<ActorDto> favouriteActorsList = actorService.getFavouriteList(user.getId());
        List<TvDto> watchedTvList = tvService.getWatchedTvList(user.getId());

        List<TvDto> recommendationTvs = favouriteActorsList.stream()
                .flatMap(actor -> actorService.getActorTvs(actor.getId()).stream().filter(tv -> !watchedTvList.contains(tv)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(recommendationTvs);
    }

}
