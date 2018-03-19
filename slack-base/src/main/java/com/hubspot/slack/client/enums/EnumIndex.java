package com.hubspot.slack.client.enums;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class EnumIndex<K, V extends Enum<V>> {
  private final ImmutableMap<K, V> index;
  private final Class<V> enumType;

  public EnumIndex(Class<V> enumType, Function<? super V, K> keyExtractor) {
    index = Maps.uniqueIndex(EnumSet.allOf(enumType), keyExtractor::apply);
    this.enumType = enumType;
  }

  public V get(K key) throws UnmappedKeyException {
    return find(key).orElseThrow(() -> new UnmappedKeyException(enumType, key));
  }

  public Optional<V> find(K key) {
    return Optional.ofNullable(index.get(key));
  }

  public ImmutableSet<K> getUnmappedKeys(Collection<K> keys) {
    return keys.stream()
        .filter(key -> !index.containsKey(key))
        .collect(toImmutableSet());
  }

  public ImmutableSet<V> getAllPresent(Collection<K> keys) {
    return keys.stream()
        .map(this::find)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toImmutableEnumSet());
  }

  private static <T> Collector<T, Builder<T>, ImmutableSet<T>> toImmutableSet() {
    return Collector.of(ImmutableSet.Builder::new,
        ImmutableSet.Builder::add,
        (left, right) -> left.addAll(right.build()),
        ImmutableSet.Builder::build);
  }

  public static <E extends Enum<E>> Collector<E, HashSet<E>, ImmutableSet<E>> toImmutableEnumSet() {
    return Collector.of(HashSet::new,
        HashSet::add,
        (left, right) -> {
          left.addAll(right);
          return left;
        },
        Sets::immutableEnumSet);
  }
}
