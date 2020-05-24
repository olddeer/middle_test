package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.service.actor.model.ActorDto;
import com.spintech.testtask.service.actor.model.CastDto;
import com.spintech.testtask.service.tmdb.TmdbApi;
import com.spintech.testtask.service.tv.model.TvDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Service
@Slf4j
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    public String popularTVShows() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/tv/popular");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }

    @Override
    public TvDto getTvById(long tvId) {
        try {
            String url = getTmdbUrl("/tv/" + tvId);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TvDto> response
                    = restTemplate.getForEntity(url, TvDto.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }

    @Override
    public Set<TvDto> getActorsTvs(long actorId) {
        try {
            String url = getTmdbUrl(format("/person/%s/tv_credits", actorId));

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CastDto> response
                    = restTemplate.getForEntity(url, CastDto.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody().getCast();
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }

    @Override
    public ActorDto getActorById(Long actorId) {
        try {
            String url = getTmdbUrl(format("/person/%s", actorId));

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ActorDto> response
                    = restTemplate.getForEntity(url, ActorDto.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }

    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        return uriBuilder.build().toString();
    }
}
