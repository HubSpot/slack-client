package com.hubspot.slack.client.examples;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.hubspot.algebra.Result;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientExampleModule;
import com.hubspot.slack.client.SlackExampleClient;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.users.SlackUser;
import com.hubspot.slack.client.models.users.UserProfile;

public class FindUserByEmail {
  private static final Logger LOG = LoggerFactory.getLogger(FindUserByEmail.class);
  private final SlackClient slackClient;

  public static void main(String[] args) {
    Optional<SlackUser> matchingUserMaybe = Guice.createInjector(new SlackClientExampleModule())
        .getInstance(FindUserByEmail.class)
        .findUser("eszabowexler@hubspot.com");
    LOG.info("Found: {}", matchingUserMaybe);
  }

  @Inject
  public FindUserByEmail(
      @SlackExampleClient SlackClient slackClient
  ) {
    this.slackClient = slackClient;
  }

  public Optional<SlackUser> findUser(String emailAddress) {
    /*
     * Since we may have to enumerate a large set, we chunk it up into pages, and handle each page separately.
     */
    Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> userPageFutures = slackClient.listUsers();

    /*
     * We'll enumerate the pages so we can short-circuit and break out early if we can
     */
    for (CompletableFuture<Result<List<SlackUser>, SlackError>> userPageFuture : userPageFutures) {
      Result<List<SlackUser>, SlackError> pageResult = userPageFuture.join();

      // If there was a problem fetching the page, it'll percolate here as a RTE
      List<SlackUser> slackUsers = pageResult.unwrapOrElseThrow();
      Optional<SlackUser> matchingUser = slackUsers.stream()
          .filter(user ->
              user.getProfile()
                  .flatMap(UserProfile::getEmail) // flatMap just says the profile could be absent, or the email could be absent
                  .filter(emailAddress::equalsIgnoreCase) // keep it only if it's the email we want...
                  .isPresent()) // and we'll filter the original SlackUser list to find the one with the profile that's me!
          .findFirst();

      if (matchingUser.isPresent()) {
        return matchingUser;
      }
    }

    return Optional.empty();
  }
}
