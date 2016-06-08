/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the ReferralSTFT License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.ReferralSTFT.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.referrals.search;

import org.rippleosi.common.repo.AbstractRepositoryFactory;
import org.springframework.stereotype.Service;

@Service
public class HSCIEDefaultReferralSearchFactory extends AbstractRepositoryFactory<HSCIEReferralSearch>
    implements HSCIEReferralSearchFactory {

    @Override
    protected HSCIEReferralSearch defaultRepository() {
        return new HSCIENotConfiguredReferralSearch();
    }

    @Override
    protected Class<HSCIEReferralSearch> repositoryClass() {
        return HSCIEReferralSearch.class;
    }
}
