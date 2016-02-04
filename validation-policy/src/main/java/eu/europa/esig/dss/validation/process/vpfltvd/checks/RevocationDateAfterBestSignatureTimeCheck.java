package eu.europa.esig.dss.validation.process.vpfltvd.checks;

import java.util.Date;

import eu.europa.esig.dss.jaxb.detailedreport.XmlValidationProcessLongTermData;
import eu.europa.esig.dss.validation.MessageTag;
import eu.europa.esig.dss.validation.policy.rules.Indication;
import eu.europa.esig.dss.validation.policy.rules.SubIndication;
import eu.europa.esig.dss.validation.process.ChainItem;
import eu.europa.esig.dss.validation.reports.wrapper.CertificateWrapper;
import eu.europa.esig.dss.validation.reports.wrapper.RevocationWrapper;
import eu.europa.esig.jaxb.policy.LevelConstraint;

public class RevocationDateAfterBestSignatureTimeCheck extends ChainItem<XmlValidationProcessLongTermData> {

	private final CertificateWrapper certificate;
	private final Date bestSignatureTime;

	public RevocationDateAfterBestSignatureTimeCheck(XmlValidationProcessLongTermData result, CertificateWrapper certificate, Date bestSignatureTime,
			LevelConstraint constraint) {
		super(result, constraint);

		this.certificate = certificate;
		this.bestSignatureTime = bestSignatureTime;
	}

	@Override
	protected boolean process() {
		RevocationWrapper revocationData = certificate.getRevocationData();
		return revocationData.getRevocationDate().after(bestSignatureTime);
	}

	@Override
	protected MessageTag getMessageTag() {
		return MessageTag.ADEST_IRTPTBST;
	}

	@Override
	protected MessageTag getErrorMessageTag() {
		return MessageTag.ADEST_IRTPTBST_ANS;
	}

	@Override
	protected Indication getFailedIndicationForConclusion() {
		return Indication.INDETERMINATE;
	}

	@Override
	protected SubIndication getFailedSubIndicationForConclusion() {
		return SubIndication.REVOKED_NO_POE;
	}

}