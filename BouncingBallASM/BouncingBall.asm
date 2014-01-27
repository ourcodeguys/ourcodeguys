; Bouncing Ball vk (c) 2013 Hoàng Vãn Khoa
	.686P
	.XMM
	include listing.inc
	.model	flat

INCLUDELIB LIBCMT
INCLUDELIB OLDNAMES

; ConstDcl
	TIME_DRAW		equ 1;
	CIRCLE_RADIUS	equ 20;
	CHAM_DINH		equ 1
	CHAM_DAY		equ 2
	CHAM_TRAI		equ 3
	CHAM_PHAI		equ 4
	CS_HREDRAW		equ 2H
	CS_VREDRAW		equ 1H
	IDI_APPLICATION	equ 32512
	IDC_ARROW		equ 32512
	WHITE_BRUSH		equ 0
	MB_ICONERROR	equ 16
	CW_USEDEFAULT	equ 80000000H
	WS_OVERLAPPEDWINDOW equ	0cf000H
	WM_CREATE		equ 1H
	WM_DESTROY		equ 2H
	WM_TIMER		equ 113H
	WM_PAINT		equ 0fH
	BLACK_PEN		equ 7
	WM_QUIT			equ	12H

; End ConstDcl

_DATA	SEGMENT
; Another way to declare array
COMM	_ps:BYTE:64
COMM	_hdc:DWORD
COMM	_rect:BYTE:16
_xCircle		DWORD	CIRCLE_RADIUS
_yCircle		DWORD	CIRCLE_RADIUS
_xPrevCircle	DWORD	CIRCLE_RADIUS
_yPrevCircle	DWORD	CIRCLE_RADIUS
_dx				DWORD	05H
_dy				DWORD	05H
_szAppName		BYTE	'Bouncing Ball', 0
szMsg			BYTE	'This program requires Windows NT!', 0
szCopyrightMsg	BYTE	'Bouncing Ball (c) 2013 khoahv@hotmail.com', 0
_DATA	ENDS

EXTERN	__imp__DispatchMessageA@4:PROC
EXTERN	__imp__TranslateMessage@4:PROC
EXTERN	__imp__GetMessageA@16:PROC
EXTERN	__imp__UpdateWindow@4:PROC
EXTERN	__imp__ShowWindow@8:PROC
EXTERN	__imp__CreateWindowExA@48:PROC
EXTERN	__imp__MessageBoxA@16:PROC
EXTERN	__imp__RegisterClassA@4:PROC
EXTERN	__imp__GetStockObject@4:PROC
EXTERN	__imp__LoadCursorA@8:PROC
EXTERN	__imp__LoadIconA@8:PROC

_TEXT	SEGMENT
; _WinMain arguments and their size in bytes:
; Do not change, note KhanhVD that '$' is not necessary
; Day la de xd vt trong stack: am la trong ham main, con duong la ngoai ham main
_hwnd$ = -72			; size = 4
_msg$ = -68				; size = 28
_wndclass$ = -40		; size = 40
_hInstance$ = 8			; size = 4
_hPrevInstance$ = 12	; size = 4
_lpszCmdLine$ = 16		; size = 4
_nCmdShow$ = 20			; size = 4
_WinMain@16 PROC

	push	ebp
	mov	ebp, esp
	sub	esp, 72

	mov	DWORD PTR _wndclass$[ebp], CS_HREDRAW+CS_VREDRAW
	mov	DWORD PTR _wndclass$[ebp+4], OFFSET _WndProc@16
	mov	DWORD PTR _wndclass$[ebp+8], 0
	mov	DWORD PTR _wndclass$[ebp+12], 0
	mov	eax, DWORD PTR _hInstance$[ebp]
	mov	DWORD PTR _wndclass$[ebp+16], eax

	push	IDI_APPLICATION
	push	0
	call	DWORD PTR __imp__LoadIconA@8
	mov	DWORD PTR _wndclass$[ebp+20], eax

	push	IDC_ARROW
	push	0
	call	DWORD PTR __imp__LoadCursorA@8
	mov	DWORD PTR _wndclass$[ebp+24], eax

	push	WHITE_BRUSH
	call	DWORD PTR __imp__GetStockObject@4
	mov	DWORD PTR _wndclass$[ebp+28], eax

	mov	DWORD PTR _wndclass$[ebp+32], 0
	mov	DWORD PTR _wndclass$[ebp+36], OFFSET _szAppName
; IF (!RegisterClass (&wndclass))
	lea	ecx, DWORD PTR _wndclass$[ebp]
	push	ecx
	call	DWORD PTR __imp__RegisterClassA@4
	movzx	edx, ax; mov cac thanh ghi k cung kich thuoc
	test	edx, edx
	jne	END_IF@WinMain

	push	MB_ICONERROR 
	push	OFFSET _szAppName
	push	OFFSET szMsg
	push	0
	call	DWORD PTR __imp__MessageBoxA@16
; return 0
	xor	eax, eax
	jmp	IF_RETURN@WinMain
; END_IF
END_IF@WinMain:

	push	0
	mov	eax, DWORD PTR _hInstance$[ebp]
	push	eax
	push	0
	push	0
	push	CW_USEDEFAULT
	push	CW_USEDEFAULT
	push	CW_USEDEFAULT
	push	CW_USEDEFAULT
	push	WS_OVERLAPPEDWINDOW
	push	OFFSET szCopyrightMsg
	push	OFFSET _szAppName
	push	0
	call	DWORD 	PTR __imp__CreateWindowExA@48
	mov	DWORD PTR _hwnd$[ebp], eax

	mov	eax, DWORD PTR _nCmdShow$[ebp]
	push	eax
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__ShowWindow@8

	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__UpdateWindow@4

WHILE@WinMain:
	push	0
	push	0
	push	0
	lea	eax, DWORD PTR _msg$[ebp]
	push	eax
	call	DWORD PTR __imp__GetMessageA@16
	test	eax, eax
	je	RETURN@WinMain

	lea	eax, DWORD PTR _msg$[ebp]
	push	eax
	call	DWORD PTR __imp__TranslateMessage@4

	lea	eax, DWORD PTR _msg$[ebp]
	push	eax
	call	DWORD PTR __imp__DispatchMessageA@4

	jmp	WHILE@WinMain
RETURN@WinMain:

	mov	eax, DWORD PTR _msg$[ebp+8]
IF_RETURN@WinMain:

	mov	esp, ebp
	pop	ebp
	ret	16
_WinMain@16 ENDP
_TEXT	ENDS

EXTERN	__imp__DefWindowProcA@16:PROC
EXTERN	__imp__InvalidateRgn@12:PROC
EXTERN	__imp__MessageBeep@4:PROC
EXTERN	__imp__PostQuitMessage@4:PROC
EXTERN	__imp__KillTimer@8:PROC
EXTERN	__imp__EndPaint@8:PROC
EXTERN	__imp__CreateSolidBrush@4:PROC
EXTERN	__imp__BeginPaint@8:PROC
EXTERN	__imp__GetClientRect@8:PROC
EXTERN	__imp__SetTimer@16:PROC

_TEXT	SEGMENT
; Cach de truy nhap nhanh stack cua _WndProc
sw142 = -12						; size = 4
sw94 = -8						; size = 4
_hwnd$ = 8						; size = 4
_msg$ = 12						; size = 4
_wParam$ = 16					; size = 4
_lParam$ = 20					; size = 4
_WndProc@16 PROC

	push	ebp
	mov	ebp, esp
	sub	esp, 12

; SWITCH (msg)
	cmp	DWORD PTR _msg$[ebp], WM_PAINT
	ja	CONTINUE_SW@WndProc@WndProc; WM_TIMER > WM_PAINT > WM_DESTROY > WM_CREATE => faster
	cmp	DWORD PTR _msg$[ebp], WM_PAINT
	je	WM_PAINT@WndProc
	cmp	DWORD PTR _msg$[ebp], WM_CREATE
	je	WM_CREATE@WndProc
	cmp	DWORD PTR _msg$[ebp], WM_DESTROY
	je	WM_DESTROY@WndProc
	jmp	END_SW@WndProc
CONTINUE_SW@WndProc@WndProc:
	cmp	DWORD PTR _msg$[ebp], WM_TIMER
	je	WM_TIMER@WndProc
	jmp	END_SW@WndProc

WM_CREATE@WndProc:
	push	0
	push	10
	push	TIME_DRAW
	mov	ecx, DWORD PTR _hwnd$[ebp]
	push	ecx
	call	DWORD PTR __imp__SetTimer@16

	push	OFFSET _rect
	mov	edx, DWORD PTR _hwnd$[ebp]
	push	edx
	call	DWORD PTR __imp__GetClientRect@8

	xor	eax, eax
	jmp	RETURN@WndProc
WM_PAINT@WndProc:

	push	OFFSET _rect
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__GetClientRect@8

	push	OFFSET _rect
	call	_vkNewRect
	add	esp, 4

	push	OFFSET _ps
	mov	ecx, DWORD PTR _hwnd$[ebp]
	push	ecx
	call	DWORD PTR __imp__BeginPaint@8
	mov	DWORD PTR _hdc, eax

	push	CIRCLE_RADIUS
	mov	eax, DWORD PTR _yCircle
	push	eax
	mov	eax, DWORD PTR _xCircle
	push	eax
	push	BLACK_PEN;>\
	call	DWORD PTR __imp__GetStockObject@4
	push	eax
	push	334330;RGB(250, 25, 5) = 334330
	call	DWORD PTR __imp__CreateSolidBrush@4
	push	eax
	mov	eax, DWORD PTR _hdc
	push	eax
	call	_vkDrawCircle
	add	esp, 24

	push	OFFSET _rect
	push	CIRCLE_RADIUS
	mov	eax, DWORD PTR _yPrevCircle
	add	eax, DWORD PTR _dy
	push	eax
	mov	eax, DWORD PTR _xPrevCircle
	add	eax, DWORD PTR _dx
	push	eax
	call	_vkCompute
	add	esp, 16
	sub	eax, 1
; SWITCH (vkCompute())
	cmp	eax, 3
	ja	END_SUB_SW@WndProc
	mov	edx, eax
	jmp	DWORD PTR CASE_MULTI@WndProc[edx*4]
	
;this is CASE_MULTI@WndProc:
	;DWORD	CHAM_DOC@WndProc
	;DWORD	CHAM_DOC@WndProc
	;DWORD	CHAM_NGANG@WndProc
	;DWORD	CHAM_NGANG@WndProc

CHAM_DOC@WndProc:
	mov	eax, DWORD PTR _yPrevCircle
	cmp	eax, DWORD PTR _rect+12
	jae	END_IF1@WndProc
	mov	ecx, DWORD PTR _yPrevCircle
	cmp	ecx, DWORD PTR _rect+4
	jbe	END_IF1@WndProc

	mov	edx, DWORD PTR _dy
	neg	edx
	mov	DWORD PTR _dy, edx
END_IF1@WndProc:

	jmp	END_SUB_SW@WndProc
CHAM_NGANG@WndProc:

	mov	eax, DWORD PTR _xPrevCircle
	cmp	eax, DWORD PTR _rect+8
	jae	END_IF2@WndProc
	mov	ecx, DWORD PTR _xPrevCircle
	cmp	ecx, DWORD PTR _rect
	jbe	END_IF2@WndProc

	mov	edx, DWORD PTR _dx
	neg	edx
	mov	DWORD PTR _dx, edx

END_IF2@WndProc:
END_SUB_SW@WndProc:

	mov	eax, DWORD PTR _xCircle
	mov	DWORD PTR _xPrevCircle, eax
	mov	ecx, DWORD PTR _yCircle
	mov	DWORD PTR _yPrevCircle, ecx

	mov	edx, DWORD PTR _xCircle
	add	edx, DWORD PTR _dx
	mov	DWORD PTR _xCircle, edx
	mov	eax, DWORD PTR _yCircle
	add	eax, DWORD PTR _dy
	mov	DWORD PTR _yCircle, eax

	push	OFFSET _ps
	mov	ecx, DWORD PTR _hwnd$[ebp]
	push	ecx
	call	DWORD PTR __imp__EndPaint@8

	xor	eax, eax
	jmp	RETURN@WndProc
WM_DESTROY@WndProc:

	push	TIME_DRAW
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__KillTimer@8

	push	WM_QUIT
	call	DWORD PTR __imp__PostQuitMessage@4

	xor	eax, eax
	jmp	RETURN@WndProc
WM_TIMER@WndProc:

	push	1
	push	0
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__InvalidateRgn@12

	push	OFFSET _rect
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__GetClientRect@8

	xor	eax, eax
	jmp	RETURN@WndProc

END_SW@WndProc:
	mov	eax, DWORD PTR _lParam$[ebp]
	push	eax
	mov	eax, DWORD PTR _wParam$[ebp]
	push	eax
	mov	eax, DWORD PTR _msg$[ebp]
	push	eax
	mov	eax, DWORD PTR _hwnd$[ebp]
	push	eax
	call	DWORD PTR __imp__DefWindowProcA@16
	jmp	RETURN@WndProc

	xor	eax, eax
RETURN@WndProc:
	mov	esp, ebp
	pop	ebp
	ret	16
CASE_MULTI@WndProc:
	DWORD	CHAM_DOC@WndProc
	DWORD	CHAM_DOC@WndProc
	DWORD	CHAM_NGANG@WndProc
	DWORD	CHAM_NGANG@WndProc
_WndProc@16 ENDP
_TEXT	ENDS

EXTERN	__imp__DeleteObject@4:PROC
EXTERN	__imp__Ellipse@20:PROC
EXTERN	__imp__SelectObject@8:PROC

_TEXT	SEGMENT
_hdc$ = 8						; size = 4
_hBrush$ = 12					; size = 4
_hPen$ = 16						; size = 4
_x$ = 20						; size = 4
_y$ = 24						; size = 4
_r$ = 28						; size = 4
_vkDrawCircle PROC

	push	ebp
	mov	ebp, esp

	mov	eax, DWORD PTR _hBrush$[ebp]
	push	eax
	mov	eax, DWORD PTR _hdc$[ebp]
	push	eax
	call	DWORD PTR __imp__SelectObject@8

	mov	eax, DWORD PTR _hPen$[ebp]
	push	eax
	mov	eax, DWORD PTR _hdc$[ebp]
	push	eax
	call	DWORD PTR __imp__SelectObject@8

	mov	eax, DWORD PTR _r$[ebp]
	add	eax, DWORD PTR _y$[ebp]
	push	eax
	mov	eax, DWORD PTR _r$[ebp]
	add	eax, DWORD PTR _x$[ebp]
	push	eax
	mov	eax, DWORD PTR _y$[ebp]
	sub	eax, DWORD PTR _r$[ebp]
	push	eax
	mov	eax, DWORD PTR _x$[ebp]
	sub	eax, DWORD PTR _r$[ebp]
	push	eax
	mov	eax, DWORD PTR _hdc$[ebp]
	push	eax
	call	DWORD PTR __imp__Ellipse@20

	mov	eax, DWORD PTR _hPen$[ebp]
	push	eax
	call	DWORD PTR __imp__DeleteObject@4

	mov	eax, DWORD PTR _hBrush$[ebp]
	push	eax
	call	DWORD PTR __imp__DeleteObject@4

	xor	eax, eax

	pop	ebp
	ret	0
_vkDrawCircle ENDP

_x$ = 8						; size = 4
_y$ = 12					; size = 4
_radius$ = 16				; size = 4
_lpRect$ = 20				; size = 4
_vkCompute PROC

	push	ebp
	mov	ebp, esp

	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR _y$[ebp]
	cmp	ecx, DWORD PTR [eax+4]
	jg	RETURN_2@vkCompute
	mov	eax, 1
	jmp	RETURN@vkCompute

RETURN_2@vkCompute:
	mov	edx, DWORD PTR _lpRect$[ebp]
	mov	eax, DWORD PTR _y$[ebp]
	cmp	eax, DWORD PTR [edx+12]
	jl	RETURN_3@vkCompute
	mov	eax, 2
	jmp	RETURN@vkCompute

RETURN_3@vkCompute:
	mov	ecx, DWORD PTR _lpRect$[ebp]
	mov	edx, DWORD PTR _x$[ebp]
	cmp	edx, DWORD PTR [ecx]
	jg	RETURN_4@vkCompute
	mov	eax, 3
	jmp	RETURN@vkCompute

RETURN_4@vkCompute:
	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR _x$[ebp]
	cmp	ecx, DWORD PTR [eax+8]
	jl	RETURNZ@vkCompute
	mov	eax, 4
	jmp	RETURN@vkCompute

RETURNZ@vkCompute:
	xor	eax, eax
RETURN@vkCompute:
	pop	ebp
	ret	0
_vkCompute ENDP

_lpRect$ = 8						; size = 4
_vkNewRect PROC

	push	ebp
	mov	ebp, esp

	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR [eax+12]
	sub	ecx, CIRCLE_RADIUS
	mov	edx, DWORD PTR _lpRect$[ebp]
	mov	DWORD PTR [edx+12], ecx

	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR [eax+8]
	sub	ecx, CIRCLE_RADIUS
	mov	edx, DWORD PTR _lpRect$[ebp]
	mov	DWORD PTR [edx+8], ecx

	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR [eax+4]
	add	ecx, CIRCLE_RADIUS
	mov	edx, DWORD PTR _lpRect$[ebp]
	mov	DWORD PTR [edx+4], ecx

	mov	eax, DWORD PTR _lpRect$[ebp]
	mov	ecx, DWORD PTR [eax]
	add	ecx, CIRCLE_RADIUS
	mov	edx, DWORD PTR _lpRect$[ebp]
	mov	DWORD PTR [edx], ecx

	pop	ebp
	ret	0
_vkNewRect ENDP
_TEXT	ENDS
END
