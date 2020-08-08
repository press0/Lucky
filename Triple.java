/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;


public class Triple<L, M, R> implements Comparable<Triple<L, M, R>>, Serializable {

    private static final long serialVersionUID = 1L;

    public final L left;
    public final M middle;
    public final R right;

    public static <L, M, R> Triple<L, M, R> of(final L left, final M middle, final R right) {
        return new Triple<L, M, R>(left, middle, right);
    }

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }


    public Triple(final L left, final M middle, final R right) {
        super();
        this.left = left;
        this.middle = middle;
        this.right = right;
    }


    @Override
    public int compareTo(final Triple<L, M, R> other) {
        Comparator<Triple> comparator
                = (Triple triple1, Triple triple2) ->
                triple1.getLeft().equals(triple2.getLeft())
                        &&
                        triple1.getMiddle().equals(triple2.getMiddle())
                        &&
                        triple1.getRight().equals(triple2.getRight())
                        ? 0 : -1;
        return comparator.compare(this, other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Triple<?, ?, ?>) {
            final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getMiddle(), other.getMiddle())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (getLeft() == null ? 0 : getLeft().hashCode()) ^
                (getMiddle() == null ? 0 : getMiddle().hashCode()) ^
                (getRight() == null ? 0 : getRight().hashCode());
    }

    @Override
    public String toString() {
        return new StringBuilder().append('(').append(getLeft()).append(',').append(getMiddle()).append(',')
                .append(getRight()).append(')').toString();
    }

    public String toString(final String format) {
        return String.format(format, getLeft(), getMiddle(), getRight());
    }

}

