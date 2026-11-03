package egovframework.example.sample.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.LoginVO;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	/** SampleDAO */
	private final SampleMapper sampleDAO;

	/** ID Generation */
	private final EgovIdGnrService egovIdGnrService;

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 */
	@Override
	public String insertSample(SampleVO vo) {
		LOGGER.debug(vo.toString());

		/** ID Generation Service */
		try {
			String id = egovIdGnrService.getNextStringId();
			vo.setId(id);
			LOGGER.debug(vo.toString());

			sampleDAO.insertSample(vo);
			return id;
		} catch (Exception e) {
			throw new RuntimeException("Failed to insert sample", e);
		}
	}

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 */
	@Override
	public void updateSample(SampleVO vo) {
		sampleDAO.updateSample(vo);
	}

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 */
	@Override
	public void deleteSample(SampleVO vo) {
		sampleDAO.deleteSample(vo);
	}

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 */
	@Override
	public SampleVO selectSample(SampleVO vo) {
		SampleVO resultVO = sampleDAO.selectSample(vo);
		if (resultVO == null) {
			throw new RuntimeException("info.nodata.msg");
		}
		return resultVO;
	}

	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 */
	@Override
	public List<?> selectSampleList(SampleVO searchVO) {
		return sampleDAO.selectSampleList(searchVO);
	}

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
		return sampleDAO.selectSampleListTotCnt(searchVO);
	}

	/**
	 * 사용자 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 LoginVO
	 * @return 조회한 사용자 정보 (없으면 null 반환)
	 */
	@Override
	public LoginVO selectUser(LoginVO vo) {
		return sampleDAO.selectUser(vo);
	}

}
