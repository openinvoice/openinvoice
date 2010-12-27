package org.openinvoice.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class OrderedPair <A, B> {
  private final AtomicReference<A> first;
  private final AtomicReference<B> second;
  private transient final AtomicInteger hash;

  public OrderedPair(A fst, B snd) {
    this.first = new AtomicReference<A>(fst);
    this.second = new AtomicReference<B>(snd);
    this.hash = new AtomicInteger(computeHash());
  }

  public A getFirst() {
    return first.get();
  }

  public B getSecond() {
    return second.get();
  }

  @Override
  public String toString() {
    return "(" + getClass().getName()
        + " (First: " + first.get().toString()
        + ") (Second: " + second.get().toString() + "))";
  }

  @Override
  public int hashCode() {
    return hash.get();
  }

  @Override
  public boolean equals(Object any) {
    if (this == any) {
      return true;
    }
    if (this == null) {
      return false;
    }
    if (!(any instanceof OrderedPair)) {
      return false;
    }
    AtomicReference<OrderedPair<A, B>> other;
    other = new AtomicReference<OrderedPair<A, B>>((OrderedPair<A, B>) any);
    return (first.get() == null ? other.get().first.get() == null : first.get().equals(other.get().first.get()))
        && (second.get() == null ? other.get().second.get() == null : second.get().equals(other.get().second.get()));
  }

  private int computeHash() {
    AtomicInteger firstHash = new AtomicInteger();
    AtomicInteger secondHash = new AtomicInteger();
    if (first.get() == null) {
      firstHash.set(0);
    } else {
      firstHash.set(first.get().hashCode() * 31);
    }
    if (second.get() == null) {
      secondHash.set(0);
    } else {
      secondHash.set(second.get().hashCode());
    }
    return firstHash.get() + secondHash.get();
  }
}
