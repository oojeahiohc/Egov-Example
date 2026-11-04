- 테스트 화면 생성
    - src/main/resource/templates/thymeleaf/add.html
    ```
    add.html 작성
    ```
- Controller 연결
    - src/main/java/egovframework/example/sample/web/EgovSampleController.java
    ```
    @GetMapping("/sample/add")
    ```
- egovSampleList 화면에서 버튼 생성하여 테스트
    - src/main/resource/templates/thymeleaf/egovSampleList.html
    ```
    <a th:href="@{/sample/add}" class="">카테고리 등록</a>
    ```
- post 확인 테스트
    ```
    @PostMapping("/sample/add")
	@ResponseBody
	public String add(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult, Model model) {
		return sampleVO.getId();
	}
    ```