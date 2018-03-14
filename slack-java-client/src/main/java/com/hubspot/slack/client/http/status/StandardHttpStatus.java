package com.hubspot.slack.client.http.status;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum http status constants that are generally useful for checking values.
 *
 * Use the {@link HttpStatus} interface if you want to store the model in a
 * variable or pass it as an argument.
 */
public enum StandardHttpStatus implements HttpStatus {
  /*
   * Informational Responses
   */
  CONTINUE(100),
  SWITCHING_PROTOCOLS(101),
  PROCESSING(102),

  /*
   * Success Codes
   */
  OK(200),
  CREATED(201),
  ACCEPTED(202),
  NON_AUTHORITATIVE_INFORMATION(203),
  NO_CONTENT(204),
  RESET_CONTENT(205),
  PARTIAL_CONTENT(206),
  MULTI_STATUS(207),
  ALREADY_REPORTED(208),
  IM_USED(226),

  /*
   * Redirections
   */
  MULTIPLE_CHOICES(300),
  MOVED_PERMANENTLY(301),
  FOUND(302),
  SEE_OTHER(303),
  NOT_MODIFIED(304),
  USE_PROXY(305),
  TEMPORARY_REDIRECT(307),
  PERMANENT_REDIRECT(308),

  /*
   * Client errors
   */
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  PAYMENT_REQUIRED(402),
  FORBIDDEN(403),
  NOT_FOUND(404),
  METHOD_NOT_ALLOWED(405),
  NOT_ACCEPTABLE(406),
  PROXY_AUTHENTICATION_REQUIRED(407),
  REQUEST_TIMEOUT(408),
  CONFLICT(409),
  GONE(410),
  LENGTH_REQUIRED(411),
  PRECONDITION_FAILED(412),
  REQUEST_ENTITY_TOO_LARGE(413),
  REQUEST_URI_TOO_LONG(414),
  UNSUPPORTED_MEDIA_TYPE(415),
  REQUESTED_RANGE_NOT_SATISFIABLE(416),
  EXPECTATION_FAILED(417),
  IM_A_TEAPOT(418),
  MISDIRECTED_REQUEST(421),
  UNPROCESSABLE_ENTITY(422),
  LOCKED(423),
  FAILED_DEPENDENCY(424),
  UPGRADE_REQUIRED(426),
  PRECONDITION_REQUIRED(428),
  TOO_MANY_REQUESTS(429),
  REQUEST_HEADERS_FIELDS_TOO_LARGE(431),
  UNAVAILABLE_FOR_LEGAL_REASONS(451),

  /*
   * Server errors
   */
  INTERNAL_SERVER_ERROR(500),
  NOT_IMPLEMENTED(501),
  BAD_GATEWAY(502),
  SERVICE_UNAVAILABLE(503),
  GATEWAY_TIMEOUT(504),
  HTTP_VERSION_NOT_SUPPORTED(505),
  VARIANT_ALSO_NEGOTIATES(506),
  INSUFFICIENT_STORAGE(507),
  LOOP_DETECTED(508),
  NOT_EXTENDED(510),
  NETWORK_AUTHENTICATION_REQUIRED(511),
  ;

  private static final Map<Integer, StandardHttpStatus> INDEX = Collections.unmodifiableMap(
      Stream.of(values()).collect(Collectors.toMap(StandardHttpStatus::getCode, Function.identity())));

  private final int code;
  private final HttpStatusFamily family;

  StandardHttpStatus(int code) {
    this.code = code;
    this.family = HttpStatusFamily.getFamily(code);
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public HttpStatusFamily getFamily() {
    return family;
  }

  /**
   * External callers should use {@link HttpStatus#forCode(int)} instead.
   *
   * This is package-private to simplify the enum's API and to encourage users
   * to pass around {@link HttpStatus} instead of this enum.
   */
  static Optional<HttpStatus> lookup(int code) {
    return Optional.ofNullable(INDEX.get(code));
  }
}

