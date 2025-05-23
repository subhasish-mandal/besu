/*
 * Copyright contributors to Besu.
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
package org.hyperledger.besu.consensus.qbft.adaptor;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import org.hyperledger.besu.consensus.common.bft.BftExtraData;
import org.hyperledger.besu.consensus.qbft.QbftExtraDataCodec;
import org.hyperledger.besu.consensus.qbft.core.types.QbftBlock;
import org.hyperledger.besu.ethereum.core.Block;
import org.hyperledger.besu.ethereum.core.BlockBody;
import org.hyperledger.besu.ethereum.core.BlockHeader;
import org.hyperledger.besu.ethereum.core.BlockHeaderTestFixture;
import org.hyperledger.besu.ethereum.rlp.BytesValueRLPInput;
import org.hyperledger.besu.ethereum.rlp.BytesValueRLPOutput;
import org.hyperledger.besu.ethereum.rlp.RLPInput;

import java.util.Optional;

import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;

class QbftBlockCodecAdaptorTest {

  @Test
  void canRoundTripBlock() {
    BftExtraData bftExtraData =
        new BftExtraData(Bytes.wrap(new byte[32]), emptyList(), Optional.empty(), 0, emptyList());
    Bytes encodedExtraData = new QbftExtraDataCodec().encode(bftExtraData);
    BlockHeader header = new BlockHeaderTestFixture().extraData(encodedExtraData).buildHeader();
    Block besuBlock = new Block(header, BlockBody.empty());
    QbftBlock block = new QbftBlockAdaptor(besuBlock);

    BytesValueRLPOutput rlpOutput = new BytesValueRLPOutput();
    QbftBlockCodecAdaptor qbftBlockCodec = new QbftBlockCodecAdaptor(new QbftExtraDataCodec());
    qbftBlockCodec.writeTo(block, rlpOutput);

    RLPInput rlpInput = new BytesValueRLPInput(rlpOutput.encoded(), false);
    QbftBlock decodedBlock = qbftBlockCodec.readFrom(rlpInput);
    assertThat(decodedBlock).isEqualTo(block);
  }
}
