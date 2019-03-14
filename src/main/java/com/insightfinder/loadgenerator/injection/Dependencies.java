package com.insightfinder.loadgenerator.injection;

import com.insightfinder.loadgenerator.action.creator.LoadActionsCreator;
import com.insightfinder.loadgenerator.service.PersistentService;
import com.insightfinder.loadgenerator.store.LoadStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Dependencies {

  @Bean
  public LoadActionsCreator loadActionsCreator() {
    return LoadActionsCreator.getInstance();
  }

  @Bean
  public LoadStore loadStore() {
    return LoadStore.getInstance();
  }

  @Bean
  public PersistentService persistentService() {
    return PersistentService.getInstance(loadActionsCreator());
  }
}
