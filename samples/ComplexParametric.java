/*
 * Copyright 2020 The jspecify Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jspecify.annotations.NullAware;
import org.jspecify.annotations.Nullable;

@NullAware
class ComplexParametric {
  interface SuperSuper<T extends @Nullable Object> {
    Lib<T> t();

    Lib<@Nullable T> tUnionNull();

    default void checkT(Lib<T> lib) {}

    default void checkTUnionNull(Lib<@Nullable T> lib) {}
  }

  // TODO(cpovirk): Make these abstract somewhere?

  static void checkNeverNull(Lib<? extends Object> lib) {}

  interface SuperNeverNever<T extends Object & Foo> extends SuperSuper<T> {
    default void x() {
      checkNeverNull(t());
      checkT(t());
      // MISMATCH
      checkTUnionNull(t());

      // MISMATCH
      checkNeverNull(tUnionNull());
      // MISMATCH
      checkT(tUnionNull());
      checkTUnionNull(tUnionNull());
    }
  }

  interface SuperNeverUnionNull<T extends Object & @Nullable Foo> extends SuperSuper<T> {
    default void x() {
      checkNeverNull(t());
      checkT(t());
      // MISMATCH
      checkTUnionNull(t());

      // MISMATCH
      checkNeverNull(tUnionNull());
      // MISMATCH
      checkT(tUnionNull());
      checkTUnionNull(tUnionNull());
    }
  }

  interface SuperUnionNullNever<T extends @Nullable Object & Foo> extends SuperSuper<T> {
    default void x() {
      checkNeverNull(t());
      checkT(t());
      // MISMATCH
      checkTUnionNull(t());

      // MISMATCH
      checkNeverNull(tUnionNull());
      // MISMATCH
      checkT(tUnionNull());
      checkTUnionNull(tUnionNull());
    }
  }

  interface SuperUnionNullUnionNull<T extends @Nullable Object & @Nullable Foo>
      extends SuperSuper<T> {
    default void x() {
      // MISMATCH
      checkNeverNull(t());
      checkT(t());
      // MISMATCH
      checkTUnionNull(t());

      // MISMATCH
      checkNeverNull(tUnionNull());
      // MISMATCH
      checkT(tUnionNull());
      checkTUnionNull(tUnionNull());
    }
  }

  interface Foo {}

  interface Lib<T extends @Nullable Object> {}
}