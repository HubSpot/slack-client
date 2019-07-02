# Slack Web API Breaking Changes

[![license](https://img.shields.io/github/license/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/commits/master)
 [![Build Status](https://travis-ci.org/HubSpot/slack-client.svg?branch=master)](https://travis-ci.org/HubSpot/slack-client) [![GitHub release](https://img.shields.io/github/release/HubSpot/slack-client.svg)](https://github.com/HubSpot/slack-client/releases) ![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/hubspot/slack/slack-client/maven-metadata.xml.svg)

* [Overview](#overview)
* [Changelogs](#changelogs)
* [Audit](#audit)
* [History](#history)

## Overview

Every so often, companies will issue “breaking changes” whether they’re officially calling them that or not.
You can think of a breaking change as any modification or deletion of an api endpoint that would adversely affect the `slack-client`.

The main reason for this process is to be proactive and prevent possible future bugs in the `slack-client`.

## Changelogs

There are 2 changelogs: current and future.

* [Current](https://api.slack.com/changelog)
* [Future](https://api.slack.com/changelog/future)

The former contains recent changes and dates on when an endpoint will be changed in a possible breaking way.
The latter shows proposed future changes.

Both should be read through when determining if the changes affect us.

## Audit

The following is a list of proposed steps to take in order to make sure that `slack-client` stays stable and on the latest working Slack Web API.

* Read the docs above for possible “breaking changes”. It's generally a good idea to go back a few months and see if anything changed. See `History` and don't revisit the same stuff.
* Look for red APIs found throughout their docs above. These indicate the change could be breaking.
* Every month, generate a list of changes to look out for.Make sure that the prior months' lists didn't already include the same breaking change. We don’t want to verify the same change twice.
* For each change, we should audit the `slack-client` code and see if it affects us.
* If we find anything that requires further investigation or actual code changes, an issue should be logged [here](https://github.com/HubSpot/slack-client/issues) with the label `breaking change`.
* If HubSpot is making the change, it's recommended that an internal ZenHub task is created to track this as well. Reference the github issue above.
* We should aim to wrap up issues with several weeks to spare. If a deadline for a breaking change is the end of June, we should have something ready in early June if at all possible.
* We should add a record of this audit to `History` table. Make sure to document where in changelog docs you went to so we can reference next time and not revisit.

## History

| Date | Found Issues | Description |
| ---- | ------------ | ----------- |
| 20190702 | One small deprecation | Audited up to July 08 2019 tasks in https://api.slack.com/changelog/future |
