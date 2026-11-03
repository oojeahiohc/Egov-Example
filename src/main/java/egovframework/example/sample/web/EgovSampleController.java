package egovframework.example.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.pagination.EgovKrdsPaginationRenderer;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.EgovUserDetailsService;
import egovframework.example.sample.service.LoginVO;
import egovframework.example.sample.service.SampleVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EgovSampleController {

	/** EgovSampleService */
	private final EgovSampleService sampleService;

	/** EgovPropertyService */
	private final EgovPropertyService propertiesService;

	/** EgovUserDetailsService */
	private final EgovUserDetailsService userDetailsService;

	/** EgovKrdsPaginationRenderer */
	private final EgovKrdsPaginationRenderer egovKrdsPaginationRenderer;

	@GetMapping("/")
	public String search(@ModelAttribute SampleVO sampleVO, HttpServletRequest request, Model model) {
		Object loginVO = userDetailsService.getAuthenticatedUser();

		if (ObjectUtils.isEmpty(loginVO)) {
			return "redirect:/sample/login";
		}

		if (loginVO instanceof LoginVO) {
			LoginVO user = (LoginVO) loginVO;
			model.addAttribute("username", user.getUsername());
		}

		return "egovSampleList";
	}

	@GetMapping("/sample/hello")
	@ResponseBody
	public String hello() {
		return "Hello, 전자정부프레임워크 5.0";
	}

	@GetMapping("/sample/list")
	public String listGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("errorType", "methodNotAllowed");
		return "redirect:/sample/accessDenied";
	}

	@PostMapping("/sample/list")
	public ResponseEntity<?> list(@ModelAttribute SampleVO sampleVO, Model model) {

		/** EgovPropertyService.sample */
		sampleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		sampleVO.setPageSize(propertiesService.getInt("pageSize"));

		/** Pagination Setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		/** List */
		List<?> list =  sampleService.selectSampleList(sampleVO);

		/** Count */
		paginationInfo.setTotalRecordCount(sampleService.selectSampleListTotCnt(sampleVO));

		/** Pagination */
        String pagination = egovKrdsPaginationRenderer.renderPagination(paginationInfo, "samplePagination");

        Map<String, Object> response = new HashMap<>();
        response.put("sampleList", list);
        response.put("pagination", pagination);
        response.put("totalCount", paginationInfo.getTotalRecordCount()+1);
        response.put("lineNumber", (sampleVO.getPageIndex()-1) * sampleVO.getPageSize());

        return ResponseEntity.ok(response);
	}

	@GetMapping("/sample/detail")
	public String detailGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("errorType", "methodNotAllowed");
		return "redirect:/sample/accessDenied";
	}

	@PostMapping("/sample/detail")
	public String detail(@ModelAttribute SampleVO sampleVO, @RequestParam String id, Model model) {
		sampleVO.setId(id);
		SampleVO detail = this.sampleService.selectSample(sampleVO);
		model.addAttribute("sampleVO", detail);
		return "egovSampleRegister";
	}

	@GetMapping("/sample/add")
	public String form(@ModelAttribute SampleVO sampleVO, Model model) {
		return "egovSampleRegister";
	}

	@PostMapping("/sample/add")
	public String add(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "egovSampleRegister";
		}
		this.sampleService.insertSample(sampleVO);
		return "redirect:/";
	}

	@GetMapping("/sample/update")
	public String updateGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("errorType", "methodNotAllowed");
		return "redirect:/sample/accessDenied";
	}

	@PostMapping("/sample/update")
	public String update(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "egovSampleRegister";
		}
		this.sampleService.updateSample(sampleVO);
		return "redirect:/";
	}

	@GetMapping("/sample/delete")
	public String deleteGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("errorType", "methodNotAllowed");
		return "redirect:/sample/accessDenied";
	}

	@PostMapping("/sample/delete")
	public String delete(@ModelAttribute SampleVO sampleVO) {
		this.sampleService.deleteSample(sampleVO);
		return "redirect:/";
	}

	@GetMapping("/sample/login")
	public String login(@ModelAttribute LoginVO loginVO, Model model) {
		return "egovSampleLogin";
	}

	@GetMapping("/sample/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/sample/login";
	}

	@GetMapping("/sample/loginProcess")
	public String loginProcessGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("errorType", "methodNotAllowed");
		return "redirect:/sample/accessDenied";
	}

	@PostMapping("/sample/loginProcess")
	public String loginProcess(@ModelAttribute LoginVO loginVO, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

		// 아이디 입력 체크
		if (loginVO.getUsername() == null || loginVO.getUsername().trim().isEmpty()) {
			redirectAttributes.addAttribute("errorType", "loginFailure");
			return "redirect:/sample/accessDenied";
		}
		
		// 비밀번호 입력 체크
		if (loginVO.getPassword() == null || loginVO.getPassword().trim().isEmpty()) {
			redirectAttributes.addAttribute("errorType", "loginFailure");
			return "redirect:/sample/accessDenied";
		}

		// 사용자 정보 조회
		LoginVO userInfo = sampleService.selectUser(loginVO);

		// 사용자 존재 여부 체크
		if (userInfo == null || userInfo.getUsername() == null) {
			redirectAttributes.addAttribute("errorType", "loginFailure");
			return "redirect:/sample/accessDenied";
		}

		// 비밀번호 일치 여부 체크
		if (userInfo.getPassword() == null || !loginVO.getPassword().equals(userInfo.getPassword())) {
			redirectAttributes.addAttribute("errorType", "loginFailure");
			return "redirect:/sample/accessDenied";
		}

		// 로그인 성공 - 세션 설정
		userInfo.setUserId(userInfo.getUsername());
		
		// 세션에 저장
		// 1. loginVO 객체 저장 (필수)
		request.getSession().setAttribute("loginVO", userInfo);
		
		// 2. userId 별도 저장 (eGovFrame 호환성을 위해)
		request.getSession().setAttribute("userId", userInfo.getUserId());
		
		// 3. 기존 accessUser도 유지
		request.getSession().setAttribute("accessUser", userInfo.getUsername());

		// 목록 페이지를 직접 반환 (리다이렉트 시 세션 문제 방지)
		SampleVO sampleVO = new SampleVO();
		model.addAttribute("sampleVO", sampleVO);
		
		// 템플릿에서 사용자 정보를 표시하기 위해 Model에 추가
		model.addAttribute("username", userInfo.getUsername());
		
		return "egovSampleList";
	}

	@GetMapping("/sample/accessDenied")
	public String accessDenied(@RequestParam(required = false) String errorType, Model model) {

		// errorType이 없으면 기본값 설정
		if (errorType == null || errorType.isEmpty()) {
			errorType = "accessDenied";
		}

		model.addAttribute("errorType", errorType);
		
		return "egovSampleError";
	}

}
