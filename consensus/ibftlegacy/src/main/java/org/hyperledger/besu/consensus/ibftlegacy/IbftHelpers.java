/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.consensus.ibftlegacy;

import org.hyperledger.besu.datatypes.Hash;

/** The Ibft helpers utility class. */
public class IbftHelpers {
  /** Default constructor */
  public IbftHelpers() {}

  /** The constant EXPECTED_MIX_HASH. */
  public static final Hash EXPECTED_MIX_HASH =
      Hash.fromHexString("0x63746963616c2062797a616e74696e65206661756c7420746f6c6572616e6365");

  /**
   * Calculate required validator quorum.
   *
   * @param validatorCount the validator count
   * @return the int
   */
  public static int calculateRequiredValidatorQuorum(final int validatorCount) {
    final int F = (validatorCount - 1) / 3;
    return (2 * F) + 1;
  }
}
