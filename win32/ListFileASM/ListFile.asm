; List File Dialog (c) 2013 khoahv@hotmail.com
.686P
.XMM
include listing.inc
include ListFile.inc
.model	flat

INCLUDELIB LIBCMT
INCLUDELIB OLDNAMES

_DATA	SEGMENT
_hThread		DWORD	?
_hListPath		DWORD	?
_hBtnScanStop	DWORD	?
_hTextBox		DWORD	?
_szPath			BYTE	MAX_PATH DUP(?)
_hWnd			DWORD	?
_iThreadID		DWORD	?

_iAttr			DWORD	?
_bFlag			DWORD	?
_hFile			DWORD	?

szStop			BYTE	'Stop', 0
szScan			BYTE	'Scan', 0
szWildString	BYTE	'\*.*', 0
szMsg			BYTE	'Message', 0
szDone			BYTE	'Done!', 0

_DATA	ENDS

EXTERN	__imp__DialogBoxParamA@20:PROC

_TEXT	SEGMENT

_hInstance$ = 8						; size = 4
_hPrevInstance$ = 12				; size = 4
_lpCmdLine$ = 16					; size = 4
_nCmdShow$ = 20						; size = 4
_WinMain@16 PROC
	push	ebp
	mov	ebp, esp

	push	0
	push	OFFSET _DlgProc@16
	mov	eax, DWORD PTR _hWnd
	push	eax
	push	IDD_DLG_FL
	mov	ecx, DWORD PTR _hInstance$[ebp]
	push	ecx
	call	DWORD PTR __imp__DialogBoxParamA@20
; return 0;
	xor	eax, eax
	pop	ebp
	ret	16
_WinMain@16 ENDP
_TEXT	ENDS

EXTERN	__imp__PostQuitMessage@4:PROC
EXTERN	__imp__SendMessageA@16:PROC
EXTERN	__imp__EnableWindow@8:PROC
EXTERN	__imp__CreateThread@24:PROC
EXTERN	_strcat:PROC
EXTERN	__imp__GetWindowTextA@12:PROC
EXTERN	__imp__SetWindowTextA@8:PROC
EXTERN	__imp__EndDialog@8:PROC
EXTERN	__imp__SetFocus@4:PROC
EXTERN	__imp__GetDlgItem@8:PROC
EXTERN	__imp__TerminateThread@8:PROC
EXTERN	__imp__ExitThread@4:PROC

_TEXT	SEGMENT
_hWndDlg$ = 8					; size = 4
_Msg$ = 12						; size = 4
_wParam$ = 16					; size = 4
_lParam$ = 20					; size = 4
_DlgProc@16 PROC
	push	ebp
	mov	ebp, esp
	sub	esp, 8

	cmp	DWORD PTR _Msg$[ebp], WM_INITDIALOG
	ja	CONTINUE_SW@DlgProc
	cmp	DWORD PTR _Msg$[ebp], WM_INITDIALOG
	je	WM_INITDIALOG@DlgProc
	cmp	DWORD PTR _Msg$[ebp], WM_DESTROY
	je	WM_DESTROY@DlgProc
	cmp	DWORD PTR _Msg$[ebp], WM_SETFOCUS
	je	WM_SETFOCUS@DlgProc
	jmp	END_SW@DlgProc
CONTINUE_SW@DlgProc:
	cmp	DWORD PTR _Msg$[ebp], WM_COMMAND
	je	WM_COMMAND@DlgProc
	jmp	END_SW@DlgProc

WM_INITDIALOG@DlgProc:
	mov	DWORD PTR _iAttr, DDL_READWRITE

	push	IDC_BTN_SS
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	mov	DWORD PTR _hBtnScanStop, eax

	push	IDC_LIST_PATH
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	mov	DWORD PTR _hListPath, eax

	push	IDC_EDIT_PATH
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	mov	DWORD PTR _hTextBox, eax

	mov	ecx, DWORD PTR _hTextBox
	push	ecx
	call	DWORD PTR __imp__SetFocus@4
; return TRUE;
	mov	eax, 1
	jmp	RETURN@DlgProc

WM_SETFOCUS@DlgProc:
	push	IDC_EDIT_PATH
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SetFocus@4
; return TRUE;
	mov	eax, 1
	jmp	RETURN@DlgProc

WM_COMMAND@DlgProc:
	mov	ecx, DWORD PTR _wParam$[ebp]
	sub	ecx, IDC_BTN_SS
	mov	DWORD PTR _wParam$[ebp], ecx
	cmp	DWORD PTR _wParam$[ebp], 9
	ja	END_SUB_SW@DlgProc
	mov	edx, DWORD PTR _wParam$[ebp]
	jmp	DWORD PTR CASE_MULTI@DlgProc[edx*4]

IDC_BTN_CLOSE@DlgProc:
	push	0
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__EndDialog@8

	mov	eax, 1
	jmp	RETURN@DlgProc
IDC_BTN_SS@DlgProc:
	cmp	DWORD PTR _bFlag, 0
	jne	ELSE_STOP@DlgProc
; Scan Btn Hit
	push	OFFSET szStop
	mov	ecx, DWORD PTR _hBtnScanStop
	push	ecx
	call	DWORD PTR __imp__SetWindowTextA@8

	push	MAX_PATH
	push	OFFSET _szPath
	mov	edx, DWORD PTR _hTextBox
	push	edx
	call	DWORD PTR __imp__GetWindowTextA@12

	push	OFFSET szWildString
	push	OFFSET _szPath
	call	_strcat
	add	esp, 8

	push	OFFSET _iThreadID
	push	0
	push	0
	push	OFFSET _vkListFile@4
	push	0
	push	0
	call	DWORD PTR __imp__CreateThread@24
	mov	DWORD PTR _hThread, eax

	push	0
	push	IDC_CHCK_HIDDEN
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	0
	push	IDC_CHCK_SYSTEM
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	0
	push	IDC_CHCK_NORMAL
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	0
	push	IDC_CHCK_ALL
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	0
	push	IDC_EDIT_PATH
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	jmp	REVERSE_bFlag@DlgProc
ELSE_STOP@DlgProc:

	push	OFFSET szScan
	mov	edx, DWORD PTR _hBtnScanStop
	push	edx
	call	DWORD PTR __imp__SetWindowTextA@8

	push	0
	mov	eax, DWORD PTR _hThread
	push	eax
	call	DWORD PTR __imp__TerminateThread@8

	push	1
	push	IDC_CHCK_HIDDEN
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_SYSTEM
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_NORMAL
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_ALL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_EDIT_PATH
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

REVERSE_bFlag@DlgProc:
	xor	eax, eax
	cmp	DWORD PTR _bFlag, 0
	sete	al
	mov	DWORD PTR _bFlag, eax

	jmp	END_SUB_SW@DlgProc
IDC_CHCK_NORMAL@DlgProc:

	push	0
	push	0
	push	BM_GETCHECK
	push	IDC_CHCK_NORMAL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16
	test	eax, eax
	je	ELSE2@DlgProc

	mov	DWORD PTR _iAttr, DDL_READWRITE

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_HIDDEN
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_SYSTEM
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_ALL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
ELSE2@DlgProc:

	push	0
	push	1
	push	BM_SETCHECK
	push	IDC_CHCK_HIDDEN
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	1
	push	BM_SETCHECK
	push	IDC_CHCK_SYSTEM
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
IDC_CHCK_HIDDEN@DlgProc:

	push	0
	push	0
	push	BM_GETCHECK
	push	IDC_CHCK_HIDDEN
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16
	test	eax, eax
	je	ELSE3@DlgProc

	mov	DWORD PTR _iAttr, DDL_HIDDEN

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_NORMAL
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
ELSE3@DlgProc:

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_ALL
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
IDC_CHCK_SYSTEM@DlgProc:

	push	0
	push	0
	push	BM_GETCHECK
	push	IDC_CHCK_SYSTEM
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16
	test	eax, eax
	je	ELSE4@DlgProc

	mov	DWORD PTR _iAttr, DDL_SYSTEM

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_NORMAL
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
ELSE4@DlgProc:

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_ALL
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
IDC_CHCK_ALL@DlgProc:

	push	0
	push	0
	push	BM_GETCHECK
	push	IDC_CHCK_ALL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16
	test	eax, eax
	je	ELSE5@DlgProc

	mov	DWORD PTR _iAttr, DDL_READWRITE+DDL_DIRECTORY+DDL_READONLY+DDL_SYSTEM+DDL_HIDDEN;

	push	0
	push	1
	push	BM_SETCHECK
	push	IDC_CHCK_HIDDEN
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	1
	push	BM_SETCHECK
	push	IDC_CHCK_SYSTEM
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_NORMAL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	jmp	END_SUB_SW@DlgProc
ELSE5@DlgProc:

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_HIDDEN
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	0
	push	BM_SETCHECK
	push	IDC_CHCK_SYSTEM
	mov	eax, DWORD PTR _hWndDlg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	1
	push	BM_SETCHECK
	push	IDC_CHCK_NORMAL
	mov	ecx, DWORD PTR _hWndDlg$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__SendMessageA@16
END_SUB_SW@DlgProc:

	jmp	END_SW@DlgProc
WM_DESTROY@DlgProc:

	push	0
	mov	edx, DWORD PTR _hWndDlg$[ebp]
	push	edx
	call	DWORD PTR __imp__EndDialog@8

	push	0
	call	DWORD PTR __imp__PostQuitMessage@4

	mov	eax, 1
	jmp	RETURN@DlgProc
END_SW@DlgProc:

	xor	eax, eax
RETURN@DlgProc:

	mov	esp, ebp
	pop	ebp
	ret	16
CASE_MULTI@DlgProc:
	DWORD	IDC_BTN_SS@DlgProc
	DWORD	END_SUB_SW@DlgProc
	DWORD	END_SUB_SW@DlgProc
	DWORD	END_SUB_SW@DlgProc
	DWORD	END_SUB_SW@DlgProc
	DWORD	IDC_CHCK_HIDDEN@DlgProc
	DWORD	IDC_CHCK_SYSTEM@DlgProc
	DWORD	IDC_CHCK_NORMAL@DlgProc
	DWORD	IDC_BTN_CLOSE@DlgProc
	DWORD	IDC_CHCK_ALL@DlgProc
_DlgProc@16 ENDP
_TEXT	ENDS
EXTERN	__imp__MessageBoxA@16:PROC

_TEXT	SEGMENT
_hWndDlg$ = -4						; size = 4
_pParam$ = 8						; size = 4
_vkListFile@4 PROC

	push	ebp
	mov	ebp, esp

	push	0
	push	0
	push	LB_RESETCONTENT
	mov	ecx, DWORD PTR _hListPath
	push	ecx
	call	DWORD PTR __imp__SendMessageA@16

	push	OFFSET _szPath
	mov	eax, DWORD PTR _iAttr
	push	eax
	push	LB_DIR
	mov	ecx, DWORD PTR _hListPath
	push	ecx
	call	DWORD PTR __imp__SendMessageA@16

	push	0
	push	0
	push	LBS_SORT
	mov	edx, DWORD PTR _hListPath
	push	edx
	call	DWORD PTR __imp__SendMessageA@16

	push	MB_ICONINFORMATION
	push	OFFSET szMsg
	push	OFFSET szDone
	push	0
	call	DWORD PTR __imp__MessageBoxA@16

	push	OFFSET szScan
	mov	edx, DWORD PTR _hBtnScanStop
	push	edx
	call	DWORD PTR __imp__SetWindowTextA@8

	push	1
	push	IDC_CHCK_HIDDEN
	mov	ecx, DWORD PTR _pParam$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_SYSTEM
	mov	edx, DWORD PTR _pParam$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_NORMAL
	mov	eax, DWORD PTR _pParam$[ebp]
	push	eax
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_CHCK_ALL
	mov	ecx, DWORD PTR _pParam$[ebp]
	push	ecx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	push	1
	push	IDC_EDIT_PATH
	mov	edx, DWORD PTR _pParam$[ebp]
	push	edx
	call	DWORD PTR __imp__GetDlgItem@8
	push	eax
	call	DWORD PTR __imp__EnableWindow@8

	xor	eax, eax
	cmp	DWORD PTR _bFlag, eax
	sete	al
	mov	DWORD PTR _bFlag, eax
	
	pop	ebp
	ret	4
_vkListFile@4 ENDP
_TEXT	ENDS
END
